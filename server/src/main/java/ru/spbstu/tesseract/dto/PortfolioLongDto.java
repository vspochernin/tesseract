package ru.spbstu.tesseract.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import ru.spbstu.tesseract.entity.Portfolio;

@Data
@Builder
public class PortfolioLongDto {

    private String createDateTime;
    private long currentAmount;
    private int riskTypeId;
    private List<AssetPortfolioDto> assetList;
    private long amountDiff;

    public static PortfolioLongDto fromPortfolio(Portfolio portfolio) {
        List<AssetPortfolioDto> assets = portfolio.getPortfolioAssetSet().stream()
                .map(AssetPortfolioDto::fromPortfolioAsset)
                .toList();

        return PortfolioLongDto.builder()
                .createDateTime(portfolio.getCreateDatetime().toString())
                .currentAmount(portfolio.getCurrentAmount())
                .riskTypeId(portfolio.getRiskType().ordinal())
                .assetList(assets)
                .amountDiff(portfolio.getCurrentAmount() - portfolio.getAmount())
                .build();
    }
}
