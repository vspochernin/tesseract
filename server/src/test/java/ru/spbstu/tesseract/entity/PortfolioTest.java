package ru.spbstu.tesseract.entity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PortfolioTest {
    @Test
    public void givenPortfolioAsset_whenGetCurrentAmount_returnPortfolioCurrentAmount() {
        Portfolio portfolio = Portfolio.builder()
                .portfolioAssetSet(Set.of(
                        PortfolioAsset.builder()
                                .asset(Asset.builder()
                                        .prices(List.of(
                                                        Price.builder()
                                                                .price(100)
                                                                .setDatetime(ZonedDateTime.now())
                                                                .build()
                                                )
                                        )
                                        .build())
                                .count(10)
                                .build()))
                .build();

        long actualCurrentAmount = portfolio.getCurrentAmount();

        assertThat(actualCurrentAmount).isEqualTo(1000);
    }

    @Test
    public void givenPortfolioAssetWithConstructor_whenGetCurrentAmount_returnPortfolioCurrentAmount() {
        Asset asset = Asset.builder()
                .prices(List.of(
                        Price.builder()
                                .price(100)
                                .setDatetime(ZonedDateTime.now())
                                .build()))
                .build();
        User user = new User();
        PortfolioAsset portfolioAsset = new PortfolioAsset(asset, 10);
        List<PortfolioAsset> portfolioAssets = List.of(portfolioAsset);
        Portfolio portfolio = new Portfolio(user, ZonedDateTime.now(), RiskType.LOW, 10L,
                portfolioAssets);

        long actualCurrentAmount = portfolio.getCurrentAmount();

        assertThat(actualCurrentAmount).isEqualTo(1000);
    }
}