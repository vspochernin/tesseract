package ru.spbstu.tesseract.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import ru.spbstu.tesseract.entity.Asset;
import ru.spbstu.tesseract.entity.Portfolio;
import ru.spbstu.tesseract.entity.PortfolioAsset;
import ru.spbstu.tesseract.entity.Price;
import ru.spbstu.tesseract.entity.RiskType;

import static org.assertj.core.api.Assertions.assertThat;

class PortfolioShortDtoTest {

    @Test
    public void givenPortfolio_whenFromPortfolio_thenReturnCorrectPortfolioShortDto() {
        ZonedDateTime now = ZonedDateTime.now();
        Portfolio portfolio = new Portfolio(
                null,
                now,
                RiskType.HIGH,
                500,
                List.of(new PortfolioAsset(
                        Asset.builder()
                                .prices(
                                        List.of(
                                                Price.builder()
                                                        .price(500)
                                                        .setDatetime(ZonedDateTime.of(
                                                                LocalDateTime.of(2023, 10, 10, 10, 10),
                                                                ZoneId.of("Europe/Moscow")))
                                                        .build()))
                                .build(), 2)));
        portfolio.setId(1);
        PortfolioShortDto actualPortfolioShortDto =
                PortfolioShortDto.fromPortfolio(portfolio);

        PortfolioShortDto expectedPortfolioShortDto = PortfolioShortDto.builder()
                .portfolioId(1)
                .createDatetime(now.toString())
                .riskTypeId(RiskType.HIGH.ordinal())
                .currentAmount(1000)
                .amountDiff(500)
                .build();
        assertThat(actualPortfolioShortDto).isEqualTo(expectedPortfolioShortDto);
    }

}
