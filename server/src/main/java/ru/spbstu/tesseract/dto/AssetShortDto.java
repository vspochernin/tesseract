package ru.spbstu.tesseract.dto;

import lombok.*;
import ru.spbstu.tesseract.entity.Asset;

@Data
@Builder
public class AssetShortDto {

    public static AssetShortDto fromAsset(Asset asset) {
        int assetPrice = asset.getAssetPrice();

        return AssetShortDto.builder()
                .assetId(asset.getId())
                .assetTitle(asset.getTitle())
                .companyTitle(asset.getCompany().getTitle())
                .assetPrice(assetPrice)
                .assetPriceDiff(asset.getAssetMonthPriceDiff(assetPrice))
                .favouriteStatus(asset.isAssetFavourite())
                .build();
    }

    private int assetId;
    private String assetTitle;
    private String companyTitle;
    private int assetPrice;
    private int assetPriceDiff;
    private boolean favouriteStatus;
}
