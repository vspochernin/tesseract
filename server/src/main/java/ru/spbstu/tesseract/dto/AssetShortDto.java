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
    private int assetPrice;
    private int assetPriceDiff;
    private boolean favouriteStatus;

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
}
