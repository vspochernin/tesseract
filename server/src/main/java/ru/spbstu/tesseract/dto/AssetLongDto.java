package ru.spbstu.tesseract.dto;

import lombok.Builder;
import lombok.Data;
import ru.spbstu.tesseract.entity.Asset;

@Data
@Builder
public class AssetLongDto {

    public static AssetLongDto fromAsset(Asset asset) {
        Integer assetPrice = asset.getAssetPrice();

        return AssetLongDto.builder()
                .assetId(asset.getId())
                .assetTitle(asset.getTitle())
                .assetDescription(asset.getDescription())
                .assetPrice(assetPrice)
                .assetPriceDiff(asset.getAssetMonthPriceDiff(assetPrice))
                .companyTitle(asset.getCompany().getTitle())
                .companyDescription(asset.getCompany().getDescription())
                .riskTypeId(asset.getRiskType().ordinal())
                .favouriteStatus(asset.isAssetFavourite())
                .build();
    }

    private Integer assetId;
    private String assetTitle;
    private String assetDescription;
    private Integer assetPrice;
    private Integer assetPriceDiff;
    private String companyTitle;
    private String companyDescription;
    private Integer riskTypeId;
    private Boolean favouriteStatus;
}
