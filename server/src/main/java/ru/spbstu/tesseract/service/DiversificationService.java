package ru.spbstu.tesseract.service;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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
        long amount = request.getAmount();
        if (amount > 1_000_000_000L) {
            throw new TesseractException(TesseractErrorType.TOO_BIG_AMOUNT);
        }

        int riskTypeId = request.getRiskTypeId();
        RiskType riskType = RiskType.getById(riskTypeId);

        List<Asset> assets = assetRepository.findAll().stream()
                .filter(asset -> riskType.equals(RiskType.COMBINED) || asset.getRiskType().equals(riskType))
                .collect(Collectors.toList());

        if (assets.isEmpty()) {
            throw new TesseractException(TesseractErrorType.NO_ASSETS_WITH_SUCH_RISK_TYPE);
        }

        // Сортировка активов по совокупной оценке надежности и доходности.
        assets.sort(
                Comparator.comparingDouble((Asset asset) -> asset.getAssetScore() * asset.getInterest()).reversed());

        Map<Asset, Long> assetsInDiversifications = new LinkedHashMap<>();
        long currentSumPrice = 0;

        Random random = new Random();

        while (currentSumPrice < amount && !assets.isEmpty()) {
            for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext(); ) {
                Asset asset = iterator.next();
                long assetPrice = asset.getCurrentAssetPrice();
                if (currentSumPrice + assetPrice <= amount) {
                    long maxPossibleQuantity = (amount - currentSumPrice) / assetPrice;
                    // Выбор случайного количества в пределах допустимого
                    long quantityToAdd = random.nextInt((int) Math.max(1, maxPossibleQuantity + 1));

                    // Добавляем актив и его количество в результат
                    assetsInDiversifications.putIfAbsent(asset, 0L);
                    assetsInDiversifications.put(asset, assetsInDiversifications.get(asset) + quantityToAdd);
                    currentSumPrice += assetPrice * quantityToAdd;

                    if (currentSumPrice >= amount) break; // Прекращаем добавление, если достигли суммы
                } else {
                    iterator.remove(); // Удаляем актив, который больше не подходит
                }
            }
        }

        // Преобразование в список для сохранения
        List<DiversificationAsset> diversificationAssetsList = assetsInDiversifications.entrySet().stream()
                .map(entry -> new DiversificationAsset(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        long realAmount = diversificationAssetsList.stream()
                .mapToLong(diversificationAsset -> diversificationAsset.getCount() *
                        diversificationAsset.getAsset().getCurrentAssetPrice())
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
