package ru.spbstu.tesseract.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.spbstu.tesseract.dto.AssetShortDto;
import ru.spbstu.tesseract.entity.Asset;
import ru.spbstu.tesseract.entity.User;
import ru.spbstu.tesseract.repository.AssetRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouritesService {

    private final AssetRepository assetRepository;

    public List<AssetShortDto> getFavouriteAssets(Integer pageNumber, Integer pageSize) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Slice<Asset> favouriteAssets = assetRepository
                .findByUsersContains(currentUser, PageRequest.of(pageNumber, pageSize));

        return favouriteAssets.getContent().stream()
                .map(AssetShortDto::fromAsset)
                .toList();
    }
}
