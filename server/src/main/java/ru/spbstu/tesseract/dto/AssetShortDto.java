package ru.spbstu.tesseract.dto;

import lombok.Builder;
import lombok.Data;
import ru.spbstu.tesseract.entity.Asset;

@Data
@Builder
public class AssetShortDto {

    private int assetId;
    private String assetTitle;
    private String companyTitle;
    private long assetPrice;
    private long assetPriceDiff;
    private boolean favouriteStatus;

    public static AssetShortDto fromAsset(Asset asset) {
        return AssetShortDto.builder()
                .assetId(asset.getId())
                .assetTitle(asset.getTitle())
                .companyTitle("test tile")
                .assetPrice(asset.getCurrentAssetPrice())
                .assetPriceDiff(asset.getAssetDayPriceDiff())
                .favouriteStatus(asset.isAssetFavourite())
                .build();
    }
}
