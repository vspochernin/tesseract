package ru.spbstu.tesseract.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

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
import ru.spbstu.tesseract.exception.NoAssetsWithSuchRiskTypeException;
import ru.spbstu.tesseract.exception.TooBigAmountException;
import ru.spbstu.tesseract.exception.TooLittleAmountException;
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
            throw new TooBigAmountException();
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
            throw new NoAssetsWithSuchRiskTypeException();
        }

        int minPriceOfAssetWithSuchRiskType = assets.stream()
                .map(Asset::getAssetPrice)
                .mapToInt(Integer::intValue)
                .min()
                .orElseThrow();

        if (amount < minPriceOfAssetWithSuchRiskType) {
            throw new TooLittleAmountException();
        }


        // TODO: mock, will be implemented in 48.
        List<DiversificationAsset> diversificationAssets = assets.stream()
                .filter(asset -> new Random().nextBoolean())
                .map(asset -> new DiversificationAsset(asset, new Random().nextInt(1, 11)))
                .toList();
        int actualAmount = diversificationAssets.stream()
                .mapToInt(diversificationAsset -> diversificationAsset.getCount() *
                        diversificationAsset.getAsset().getAssetPrice())
                .sum();

        User currentUser = User.getCurrentUser();

        Diversification createdDiversification = new Diversification(
                currentUser,
                ZonedDateTime.now(),
                riskType,
                actualAmount,
                diversificationAssets
        );

        diversificationRepository.save(createdDiversification);
    }
}
