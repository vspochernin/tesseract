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
    private long currentPriceSum;
    private long count;
    private boolean favouriteStatus;
    private long priceSumDiff;
    private long currentPrice;

    public static AssetDiversificationDto fromDiversificationAsset(DiversificationAsset diversificationAsset) {
        Diversification diversification = diversificationAsset.getDiversification();
        Asset asset = diversificationAsset.getAsset();

        long oldAssetPrice = asset.getOldPrice(diversification.getCreateDatetime())
                .orElseThrow(() -> new NoSuchElementException("Can't find price"));
        long currentAssetPrice = asset.getCurrentAssetPrice();

        long assetCount = diversificationAsset.getCount();

        long currentPriceSum = currentAssetPrice * assetCount;
        long oldPriceSum = oldAssetPrice * assetCount;

        return AssetDiversificationDto.builder()

                .assetId(asset.getId())
                .assetTitle(asset.getTitle())
                .companyTitle(asset.getCompany().getTitle())
                .currentPriceSum(currentPriceSum)
                .count(assetCount)
                .favouriteStatus(asset.isAssetFavourite())
                .priceSumDiff(currentPriceSum - oldPriceSum)
                .currentPrice(currentAssetPrice)
                .build();
    }
}
