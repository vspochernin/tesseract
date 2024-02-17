package ru.spbstu.tesseract.entity;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DiversificationTest {
    @Test
    public void givenDiversificationAsset_whenGetCurrentAmount_returnDiversificationCurrentAmount() {
        Diversification diversification = Diversification.builder()
                .diversificationAssetSet(Set.of(
                        DiversificationAsset.builder()
                                .Asset.builder()
                                .prices(List.of(
                                                Price.builder()
                                                        .price(100)
                                                        .setDatetime(ZonedDateTime.now())
                                                        .build()
                                        )
                                )
                                .count(10)
                                .build()))
                .build();

        long actualCurrentAmount = diversification.getCurrentAmount();

        assertThat(actualCurrentAmount).isEqualTo(1000);
    }
}