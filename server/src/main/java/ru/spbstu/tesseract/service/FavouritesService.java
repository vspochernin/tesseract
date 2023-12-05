package ru.spbstu.tesseract.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.spbstu.tesseract.dto.AssetShortDto;
import ru.spbstu.tesseract.entity.Asset;
import ru.spbstu.tesseract.entity.User;
import ru.spbstu.tesseract.repository.AssetRepository;
import ru.spbstu.tesseract.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FavouritesService {

    private final AssetRepository assetRepository;
    private final UserRepository userRepository;

    public List<AssetShortDto> getFavouriteAssets(Integer pageNumber, Integer pageSize) {
        Slice<Asset> favouriteAssets = assetRepository
                .findByUsersContains(User.getCurrentUser(), PageRequest.of(pageNumber, pageSize));

        return favouriteAssets.getContent().stream()
                .map(AssetShortDto::fromAsset)
                .toList();
    }

    public void addFavourite(Integer assetId) {
        if (!assetRepository.existsById(assetId)) {
            throw new NoSuchElementException();
        }

        User user = userRepository.getReferenceById(User.getCurrentUser().getId());
        Asset asset = assetRepository.getReferenceById(assetId);

        if (user.getFavourites().contains(asset)) {
            throw new IllegalArgumentException();
        }

        user.getFavourites().add(asset);

        userRepository.save(user);
    }

    public void removeFavourite(Integer assetId) {
        if (!assetRepository.existsById(assetId)) {
            throw new NoSuchElementException();
        }

        User user = userRepository.getReferenceById(User.getCurrentUser().getId());
        Asset asset = assetRepository.getReferenceById(assetId);

        if (!user.getFavourites().contains(asset)) {
            throw new IllegalArgumentException();
        }

        user.getFavourites().remove(asset);

        userRepository.save(user);
    }
}
