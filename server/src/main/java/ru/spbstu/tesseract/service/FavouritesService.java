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

    public List<AssetShortDto> getFavouriteAssets(int pageNumber, int pageSize) {
        Slice<Asset> favouriteAssets = assetRepository
                .findByUsersContains(User.getCurrentUser(), PageRequest.of(pageNumber, pageSize));

        return favouriteAssets.getContent().stream()
                .map(AssetShortDto::fromAsset)
                .toList();
    }

    public boolean addFavourite(int assetId) {
        if (!assetRepository.existsById(assetId)) {
            throw new NoSuchElementException();
        }

        User user = userRepository.getReferenceById(User.getCurrentUser().getId());
        Asset asset = assetRepository.getReferenceById(assetId);

        if (user.getFavourites().contains(asset)) {
            return false;
        }

        user.getFavourites().add(asset);

        userRepository.save(user);

        return true;
    }

    public void removeFavourite(int assetId) {
        if (!assetRepository.existsById(assetId)) {
            throw new NoSuchElementException();
        }

        User user = userRepository.getReferenceById(User.getCurrentUser().getId());
        Asset asset = assetRepository.getReferenceById(assetId);

        if (!user.getFavourites().contains(asset)) {
            return;
        }

        user.getFavourites().remove(asset);

        userRepository.save(user);
    }
}
