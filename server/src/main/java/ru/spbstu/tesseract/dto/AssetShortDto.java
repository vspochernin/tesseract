package ru.spbstu.tesseract.dto;

import lombok.*;
import ru.spbstu.tesseract.entity.Asset;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetShortDto {

    public static AssetShortDto fromAsset(Asset asset) {
        Integer assetPrice = asset.getAssetPrice();

        return AssetShortDto.builder()
                .assetId(asset.getId())
                .assetTitle(asset.getTitle())
                .companyTitle(asset.getCompany().getTitle())
                .assetPrice(assetPrice)
                .assetPriceDiff(asset.getAssetMonthPriceDiff(assetPrice))
                .favouriteStatus(asset.isAssetFavourite())
                .build();
    }

    private Integer assetId;
    private String assetTitle;
    private String companyTitle;
    private Integer assetPrice;
    private Integer assetPriceDiff;
    private Boolean favouriteStatus;
}
