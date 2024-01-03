package ru.spbstu.tesseract.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.spbstu.tesseract.dto.CreateDiversificationRequestDto;
import ru.spbstu.tesseract.dto.DiversificationLongDto;
import ru.spbstu.tesseract.dto.DiversificationShortDto;
import ru.spbstu.tesseract.entity.Asset;
import ru.spbstu.tesseract.entity.Diversification;
import ru.spbstu.tesseract.entity.DiversificationAsset;
import ru.spbstu.tesseract.entity.RiskType;
import ru.spbstu.tesseract.entity.User;
import ru.spbstu.tesseract.exception.TesseractErrorType;
import ru.spbstu.tesseract.exception.TesseractException;
import ru.spbstu.tesseract.repository.AssetRepository;
import ru.spbstu.tesseract.repository.DiversificationRepository;

@Service
@RequiredArgsConstructor
public class DiversificationService {

    private final DiversificationRepository diversificationRepository;
    private final AssetRepository assetRepository;

    public List<DiversificationShortDto> getDiversifications(int pageNumber, int pageSize) {
        User currentUser = User.getCurrentUser();
        Slice<Diversification> diversifications =
                diversificationRepository.findAllByUser(currentUser, PageRequest.of(pageNumber, pageSize));

        return diversifications.getContent().stream()
                .map(DiversificationShortDto::fromDiversification)
                .toList();
    }

    public DiversificationLongDto getDiversification(int diversificationId) {
        User currentUser = User.getCurrentUser();
        Optional<Diversification> diversificationO =
                diversificationRepository.findByIdAndUser(diversificationId, currentUser);

        return diversificationO
                .map(DiversificationLongDto::fromDiversification)
                .orElseThrow(NoSuchElementException::new);
    }

    public void createDiversification(CreateDiversificationRequestDto request) {
        int amount = request.getAmount();

        if (amount > 100_000_000) {
            throw new TesseractException(TesseractErrorType.TOO_BIG_AMOUNT);
        }

        int riskTypeId = request.getRiskTypeId();
        RiskType riskType = RiskType.getById(riskTypeId);

        List<Asset> assets = assetRepository.findAll();
        if (!riskType.equals(RiskType.COMBINED)) {
            assets = assets.stream()
                    .filter(asset -> asset.getRiskType().equals(riskType))
                    .toList();
        }

        if (assets.isEmpty()) {
            throw new TesseractException(TesseractErrorType.NO_ASSETS_WITH_SUCH_RISK_TYPE);
        }

        int minPriceOfAssetWithSuchRiskType = assets.stream()
                .map(Asset::getAssetPrice)
                .mapToInt(Integer::intValue)
                .min()
                .orElseThrow();

        if (amount < minPriceOfAssetWithSuchRiskType) {
            throw new TesseractException(TesseractErrorType.TOO_LITTLE_AMOUNT);
        }

        // оставляем только активы, которые меньше или равны суммы диверсификации
        assets = assets.stream()
                .filter(asset -> asset.getAssetPrice() <= amount)
                .toList();

        // стоимость активов в диверсификации - накопленная уже
        long currentSumPrice = 0;

        // порог вероятности добавления актива
        double addProbability = 0.3;

        //порог вероятности увеличения числа актива
        double plusProbability = 0.4;

        // формируемый список итоговых активов в диверсификации
        List<Asset> resultAssetsList = new ArrayList<>(Collections.emptyList());

        while (!assets.isEmpty()) {
            // получаем актив с наименьшей стоимостью
            Asset assetWithMinPrice = assets.stream()
                    .min(Comparator.comparingInt(Asset::getAssetPrice))
                    .orElseThrow();
            int minPrice = assets.stream()
                    .map(Asset::getAssetPrice)
                    .mapToInt(Integer::intValue)
                    .min()
                    .orElseThrow();
            // проверяем, что можем его добавить
            long newAmount = currentSumPrice + assetWithMinPrice.getAssetPrice();
            if (newAmount <= amount) {
                double addRandomValue = Math.random();
                // добавляем или нет актив в диверсификацию
                if (addRandomValue > addProbability || resultAssetsList.isEmpty()) {
                    currentSumPrice += assetWithMinPrice.getAssetPrice();
                    //добавляем актив в список активов диверсификации
                    resultAssetsList.add(assetWithMinPrice);
                }
                //удаляем актив с наименьшей ценой - в любом случае, иначе всегда он будет
                assets = assets.stream()
                        .filter(asset -> asset.getAssetPrice() != minPrice)
                        .toList();
            } else {
                break;
            }
        }

        // формируем словарь ключ - актив, значение - количество
        Map<Asset, Integer> assetsInDiversifications = new HashMap<>();
        for (Asset a : resultAssetsList) {
            assetsInDiversifications.put(a, 1);
        }

        int index = resultAssetsList.size();
        // добавляем дополнительных активов, чтобы сумма была максимально к указанной пользователем
        while (index != 0) {
            ListIterator<Asset> itr = resultAssetsList.listIterator(index);
            while (itr.hasPrevious()) { //один проход
                //актив с наибольшей стоимостью и далее
                Asset currentAsset = itr.previous();

                // проверяем, что можем его добавить
                long newAmount = currentSumPrice + currentAsset.getAssetPrice();
                if (newAmount <= amount) {
                    double addRandomValue = Math.random();
                    // увеличиваем количество или нет
                    if (addRandomValue > plusProbability) {
                        currentSumPrice += currentAsset.getAssetPrice();
                        //увеличиваем количество актива в списке активов диверсификации
                        assetsInDiversifications.put(currentAsset, assetsInDiversifications.get(currentAsset) + 1);
                    }
                } else {
                    index = itr.nextIndex();
                }
            }
        }

        List<DiversificationAsset> diversificationAssetsList = resultAssetsList.stream()
                .map(asset -> new DiversificationAsset(asset, assetsInDiversifications.get(asset)))
                .toList();

        int realAmount = diversificationAssetsList.stream()
                .mapToInt(diversificationAsset -> diversificationAsset.getCount() *
                        diversificationAsset.getAsset().getAssetPrice())
                .sum();

        User currentUser = User.getCurrentUser();

        Diversification createdDiversification = new Diversification(
                currentUser,
                ZonedDateTime.now(),
                riskType,
                realAmount,
                diversificationAssetsList
        );

        diversificationRepository.save(createdDiversification);
    }
}
