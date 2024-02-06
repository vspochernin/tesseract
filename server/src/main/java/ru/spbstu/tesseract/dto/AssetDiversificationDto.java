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
    private int currentPriceSum;
    private int count;
    private boolean favouriteStatus;
    private int priceSumDiff;
    private int currentPrice;

    public static AssetDiversificationDto fromDiversificationAsset(DiversificationAsset diversificationAsset) {
        Diversification diversification = diversificationAsset.getDiversification();
        Asset asset = diversificationAsset.getAsset();

        int oldAssetPrice = asset.getOldPrice(diversification.getCreateDatetime())
                .orElseThrow(() -> new NoSuchElementException("Cannot find price"));
        int currentAssetPrice = asset.getCurrentAssetPrice();
        int assetCount = diversificationAsset.getCount();
        return AssetDiversificationDto.builder()

                .assetId(asset.getId())
                .assetTitle(asset.getTitle())
                .companyTitle(asset.getCompany().getTitle())
                .currentPriceSum(currentAssetPrice * assetCount)
                .count(assetCount)
                .favouriteStatus(asset.isAssetFavourite())
                .priceSumDiff(currentAssetPrice * assetCount - oldAssetPrice * assetCount)
                .currentPrice(currentAssetPrice)
                .build();
    }
}
