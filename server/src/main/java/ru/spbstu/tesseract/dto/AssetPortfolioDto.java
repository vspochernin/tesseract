package ru.spbstu.tesseract.dto;

import java.util.NoSuchElementException;

import lombok.Builder;
import lombok.Data;
import ru.spbstu.tesseract.entity.Asset;
import ru.spbstu.tesseract.entity.Portfolio;
import ru.spbstu.tesseract.entity.PortfolioAsset;

@Data
@Builder
public class AssetPortfolioDto {

    private int assetId;
    private String assetTitle;
    private String companyTitle;
    private long currentPriceSum;
    private long count;
    private boolean favouriteStatus;
    private long priceSumDiff;
    private long currentPrice;

    public static AssetPortfolioDto fromPortfolioAsset(PortfolioAsset portfolioAsset) {
        Portfolio portfolio = portfolioAsset.getPortfolio();
        Asset asset = portfolioAsset.getAsset();

        long oldAssetPrice = asset.getOldPrice(portfolio.getCreateDatetime())
                .orElseThrow(() -> new NoSuchElementException("Can't find price"));
        long currentAssetPrice = asset.getCurrentAssetPrice();

        long assetCount = portfolioAsset.getCount();

        long currentPriceSum = currentAssetPrice * assetCount;
        long oldPriceSum = oldAssetPrice * assetCount;

        return AssetPortfolioDto.builder()

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
