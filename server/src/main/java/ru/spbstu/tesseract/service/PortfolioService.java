package ru.spbstu.tesseract.service;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.distribution.BetaDistribution;
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

    private static final double ALPHA_MIN = 2;
    private static final double ALPHA_MAX = 10;
    private static final double BETA = 2;

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
                .filter(asset -> asset.getCurrentAssetPrice() <= amount)
                .collect(Collectors.toList());

        if (assets.isEmpty()) {
            throw new TesseractException(TesseractErrorType.TOO_LITTLE_AMOUNT);
        }

        assets = assets.stream()
                .filter(asset -> riskType.equals(RiskType.COMBINED) || asset.getRiskType().equals(riskType))
                .collect(Collectors.toList());

        if (assets.isEmpty()) {
            throw new TesseractException(TesseractErrorType.NO_ASSETS_WITH_SUCH_RISK_TYPE);
        }

        // Сортировка активов по совокупной оценке надежности и доходности.
        assets.sort(
                Comparator.comparingDouble((Asset asset) -> asset.getAssetScore() * asset.getInterest()).reversed());

        Asset theMostProfitableAsset = assets.get(0);
        double maxProfit = theMostProfitableAsset.getAssetScore() * theMostProfitableAsset.getInterest();

        Map<Asset, Long> assetsInDiversifications = new HashMap<>();
        long currentSumPrice = 0;

        while (currentSumPrice < amount && !assets.isEmpty()) {
            for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext(); ) {
                Asset asset = iterator.next();
                long assetPrice = asset.getCurrentAssetPrice();
                if (currentSumPrice + assetPrice <= amount) {
                    long maxPossibleQuantity = (amount - currentSumPrice) / assetPrice;

                    // Динамически адаптируем параметры alpha и beta.
                    double currentProfit = asset.getAssetScore() * asset.getInterest();
                    double profitNormalized = currentProfit / maxProfit;
                    double alpha = ALPHA_MIN + profitNormalized * (ALPHA_MAX - ALPHA_MIN);

                    long quantityToAdd = calculateQuantityToAdd(alpha, maxPossibleQuantity);

                    // Добавляем актив и его количество в результат.
                    assetsInDiversifications.putIfAbsent(asset, 0L);
                    assetsInDiversifications.put(asset, assetsInDiversifications.get(asset) + quantityToAdd);
                    currentSumPrice += assetPrice * quantityToAdd;

                    if (currentSumPrice >= amount) break;
                } else {
                    iterator.remove(); // Удаляем актив, который больше не подходит.
                }
            }
        }

        // Преобразование в список для сохранения.
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

    private long calculateQuantityToAdd(double alpha, long maxPossibleQuantity) {
        BetaDistribution distribution = new BetaDistribution(alpha, BETA);
        double sample = distribution.sample(); // Генерируем случайное значение от 0 до 1.
        long calculatedQuantity = 1 + (long) (sample * (maxPossibleQuantity - 1)); // Адаптируем к диапазону.
        return Math.min(calculatedQuantity, maxPossibleQuantity); // Гарантируем, что не превысим maxPossibleQuantity.
    }
}
