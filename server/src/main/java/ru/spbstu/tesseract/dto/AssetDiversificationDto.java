package ru.spbstu.tesseract.dto;

import java.util.NoSuchElementException;

import lombok.Builder;
import lombok.Data;
import ru.spbstu.tesseract.entity.Asset;
import ru.spbstu.tesseract.entity.Diversification;
import ru.spbstu.tesseract.entity.DiversificationAsset;

@Data
@Builder
public class AssetDiversificationDto {

    private int assetId;
    private String assetTitle;
    private String companyTitle;
    private int oldPrice;
    private int count;
    private boolean favouriteStatus;

    public static AssetDiversificationDto fromDiversificationAsset(DiversificationAsset diversificationAsset) {
        Diversification diversification = diversificationAsset.getDiversification();
        Asset asset = diversificationAsset.getAsset();

        return AssetDiversificationDto.builder()
                .assetId(asset.getId())
                .assetTitle(asset.getTitle())
                .companyTitle(asset.getCompany().getTitle())
                .oldPrice(asset.getOldPrice(diversification.getCreateDatetime())
                        .orElseThrow(() -> new NoSuchElementException("Cannot find price")))
                .count(diversificationAsset.getCount())
                .favouriteStatus(asset.isAssetFavourite())
                .build();
    }
}
