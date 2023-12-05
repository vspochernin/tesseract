package ru.spbstu.tesseract.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.spbstu.tesseract.dto.AssetLongDto;
import ru.spbstu.tesseract.dto.AssetShortDto;
import ru.spbstu.tesseract.entity.Asset;
import ru.spbstu.tesseract.repository.AssetRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;

    public List<AssetShortDto> getAssets(int pageNumber, int pageSize) {
        Slice<Asset> assets = assetRepository.findBy(PageRequest.of(pageNumber, pageSize));

        return assets.getContent().stream()
                .map(AssetShortDto::fromAsset)
                .toList();
    }

    public AssetLongDto getAsset(int assetId) {
        Optional<Asset> assetO = assetRepository.findById(assetId);

        return assetO
                .map(AssetLongDto::fromAsset)
                .orElseThrow(NoSuchElementException::new);
    }
}
