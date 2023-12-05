package ru.spbstu.tesseract.dto;

import lombok.*;
import ru.spbstu.tesseract.auth.utils.AssetUtils;
import ru.spbstu.tesseract.entity.Asset;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetShortDto {

    public static AssetShortDto fromAsset(Asset asset) {
        Integer assetPrice = AssetUtils.getAssetPrice(asset);
        Integer assetPriceDiff = AssetUtils.getAssetMonthPriceDiff(asset, assetPrice);
        Boolean favouriteStatus = AssetUtils.isAssetFavourite(asset);

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
