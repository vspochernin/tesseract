package ru.spbstu.tesseract.dto;

import lombok.Builder;
import lombok.Data;
import ru.spbstu.tesseract.entity.Portfolio;

@Data
@Builder
public class PortfolioShortDto {

    private int portfolioId;
    private String createDatetime;
    private int riskTypeId;
    private long currentAmount;
    private long amountDiff;

    public static PortfolioShortDto fromPortfolio(Portfolio portfolio) {
        return PortfolioShortDto.builder()
                .portfolioId(portfolio.getId())
                .createDatetime(portfolio.getCreateDatetime().toString())
                .riskTypeId(portfolio.getRiskType().ordinal())
                .currentAmount(portfolio.getCurrentAmount())
                .amountDiff(portfolio.getCurrentAmount() - portfolio.getAmount())
                .build();
    }
}
