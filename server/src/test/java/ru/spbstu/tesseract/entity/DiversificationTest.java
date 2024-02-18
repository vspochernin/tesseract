package ru.spbstu.tesseract.entity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiversificationTest {
    @Test
    public void givenDiversificationAsset_whenGetCurrentAmount_returnDiversificationCurrentAmount() {
        Diversification diversification = Diversification.builder()
                .diversificationAssetSet(Set.of(
                        DiversificationAsset.builder()
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

        long actualCurrentAmount = diversification.getCurrentAmount();

        assertThat(actualCurrentAmount).isEqualTo(1000);
    }

    @Test
    public void givenDiversificationAssetWithConstructor_whenGetCurrentAmount_returnDiversificationCurrentAmount() {
        Asset asset = Asset.builder()
                .prices(List.of(
                        Price.builder()
                                .price(100)
                                .setDatetime(ZonedDateTime.now())
                                .build()))
                .build();
        User user = new User();
        DiversificationAsset diversificationAsset = new DiversificationAsset(asset, 10);
        List<DiversificationAsset> diversificationAssets = List.of(diversificationAsset);
        Diversification diversification = new Diversification(user, ZonedDateTime.now(), RiskType.LOW, 10L,
                diversificationAssets);

        long actualCurrentAmount = diversification.getCurrentAmount();

        assertThat(actualCurrentAmount).isEqualTo(1000);
    }
}