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
import ru.spbstu.tesseract.dto.CreatePortfolioRequestDto;
import ru.spbstu.tesseract.dto.PortfolioLongDto;
import ru.spbstu.tesseract.dto.PortfolioShortDto;
import ru.spbstu.tesseract.entity.Asset;
import ru.spbstu.tesseract.entity.Portfolio;
import ru.spbstu.tesseract.entity.PortfolioAsset;
import ru.spbstu.tesseract.entity.RiskType;
import ru.spbstu.tesseract.entity.User;
import ru.spbstu.tesseract.exception.TesseractErrorType;
import ru.spbstu.tesseract.exception.TesseractException;
import ru.spbstu.tesseract.repository.AssetRepository;
import ru.spbstu.tesseract.repository.PortfolioRepository;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private static final double ALPHA_MIN = 2;
    private static final double ALPHA_MAX = 10;
    private static final double BETA = 2;

    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;

    public List<PortfolioShortDto> getPortfolios(int pageNumber, int pageSize) {
        User currentUser = User.getCurrentUser();
        Slice<Portfolio> portfolios =
                portfolioRepository.findAllByUser(currentUser, PageRequest.of(pageNumber, pageSize));

        return portfolios.getContent().stream()
                .map(PortfolioShortDto::fromPortfolio)
                .toList();
    }

    public PortfolioLongDto getPortfolio(int portfolioId) {
        User currentUser = User.getCurrentUser();
        Optional<Portfolio> portfolioO =
                portfolioRepository.findByIdAndUser(portfolioId, currentUser);

        return portfolioO
                .map(PortfolioLongDto::fromPortfolio)
                .orElseThrow(NoSuchElementException::new);
    }

    public void createPortfolio(CreatePortfolioRequestDto request) {
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

        Map<Asset, Long> assetsInPortfolios = new HashMap<>();
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
                    assetsInPortfolios.putIfAbsent(asset, 0L);
                    assetsInPortfolios.put(asset, assetsInPortfolios.get(asset) + quantityToAdd);
                    currentSumPrice += assetPrice * quantityToAdd;

                    if (currentSumPrice >= amount) break;
                } else {
                    iterator.remove(); // Удаляем актив, который больше не подходит.
                }
            }
        }

        // Преобразование в список для сохранения.
        List<PortfolioAsset> portfolioAssetsList = assetsInPortfolios.entrySet().stream()
                .map(entry -> new PortfolioAsset(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        long realAmount = portfolioAssetsList.stream()
                .mapToLong(portfolioAsset -> portfolioAsset.getCount() *
                        portfolioAsset.getAsset().getCurrentAssetPrice())
                .sum();

        User currentUser = User.getCurrentUser();
        Portfolio createdPortfolio = new Portfolio(
                currentUser,
                ZonedDateTime.now(),
                riskType,
                realAmount,
                portfolioAssetsList
        );

        portfolioRepository.save(createdPortfolio);
    }

    private long calculateQuantityToAdd(double alpha, long maxPossibleQuantity) {
        BetaDistribution distribution = new BetaDistribution(alpha, BETA);
        double sample = distribution.sample(); // Генерируем случайное значение от 0 до 1.
        long calculatedQuantity = 1 + (long) (sample * (maxPossibleQuantity - 1)); // Адаптируем к диапазону.
        return Math.min(calculatedQuantity, maxPossibleQuantity); // Гарантируем, что не превысим maxPossibleQuantity.
    }
}
