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
        Integer assetPriceDiff = asset.getAssetMonthPriceDiff(assetPrice);
        Boolean favouriteStatus = asset.isAssetFavourite();

        return AssetShortDto.builder()
                .assetTitle(asset.getTitle())
                .companyTitle(asset.getCompany().getTitle())
                .assetPrice(assetPrice)
                .assetPriceDiff(assetPriceDiff)
                .favouriteStatus(favouriteStatus)
                .build();
    }

    private String assetTitle;
    private String companyTitle;
    private Integer assetPrice;
    private Integer assetPriceDiff;
    private Boolean favouriteStatus;
}
