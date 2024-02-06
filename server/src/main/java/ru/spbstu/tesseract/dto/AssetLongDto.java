package ru.spbstu.tesseract.dto;

import lombok.Builder;
import lombok.Data;
import ru.spbstu.tesseract.entity.Asset;

@Data
@Builder
public class AssetLongDto {

    private int assetId;
    private String assetTitle;
    private String assetDescription;
    private int assetPrice;
    private int assetPriceDiff;
    private String companyTitle;
    private String companyDescription;
    private int riskTypeId;
    private boolean favouriteStatus;
    private String operatorTitle;

    public static AssetLongDto fromAsset(Asset asset) {
        int assetPrice = asset.getAssetPrice();

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
                .operatorTitle(asset.getOperator().getTitle())
                .build();
    }
}
