package ru.spbstu.tesseract.entity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AssetTest {

    @Test
    public void givenAsset_whenGetCurrentAssetPrice_returnLastAssetPrice() {
        Asset asset = Asset.builder()
                .prices(List.of(
                        Price.builder()
                                .price(100)
                                .setDatetime(ZonedDateTime.now())
                                .build(),
                        Price.builder()
                                .price(50)
                                .setDatetime(ZonedDateTime.now().minusDays(1))
                                .build()))
                .build();

        long actualCurrentAssetPrice = asset.getCurrentAssetPrice();

        assertThat(actualCurrentAssetPrice).isEqualTo(100);
    }

    @Test
    public void givenAsset_whenGetAssetDayPriceDiff_thenReturnCorrectDayPriceDiff() {
        Asset asset = Asset.builder()
                .prices(List.of(
                        Price.builder()
                                .price(100)
                                .setDatetime(ZonedDateTime.now())
                                .build(),
                        Price.builder()
                                .price(50)
                                .setDatetime(ZonedDateTime.now().minusDays(1))
                                .build()))
                .build();

        long actualAssetDayPriceDiff = asset.getAssetDayPriceDiff();

        assertThat(actualAssetDayPriceDiff).isEqualTo(50);
    }

    @Test
    public void givenAsset_whenAssetPriceDiff_thenReturnCorrectPriceDiff() {
        Asset asset = Asset.builder()
                .prices(List.of(
                        Price.builder()
                                .price(100)
                                .setDatetime(ZonedDateTime.now())
                                .build(),
                        Price.builder()
                                .price(50)
                                .setDatetime(ZonedDateTime.now().minusDays(1))
                                .build(),
                        Price.builder()
                                .price(250)
                                .setDatetime(ZonedDateTime.now().minusWeeks(1))
                                .build()))
                .build();

        long actualAssetDayPriceDiff = asset.getAssetPriceDiff(ZonedDateTime.now().minusWeeks(1));

        assertThat(actualAssetDayPriceDiff).isEqualTo(-150);
    }

    @Test
    public void givenAsset_whenGetDaysSinceRelease_thenReturnCorrectDaysSinceRelease() {
        Asset asset = Asset.builder()
                .releaseDatetime(ZonedDateTime.now().minusWeeks(1))
                .build();

        long actualDaysSinceRelease = asset.getDaysSinceRelease();

        assertThat(actualDaysSinceRelease).isEqualTo(7);
    }

    @Test
    public void givenAsset_whenGetOldPrice_thenReturnCorrectOldPrice() {
        Asset asset = Asset.builder()
                .prices(List.of(
                        Price.builder()
                                .price(100)
                                .setDatetime(ZonedDateTime.now())
                                .build(),
                        Price.builder()
                                .price(50)
                                .setDatetime(ZonedDateTime.now().minusDays(1))
                                .build(),
                        Price.builder()
                                .price(250)
                                .setDatetime(ZonedDateTime.now().minusWeeks(1))
                                .build()))
                .build();

        Optional<Long> actualOldPrice = asset.getOldPrice(ZonedDateTime.now().minusDays(3));

        assertThat(actualOldPrice).isPresent();
        assertThat(actualOldPrice.get()).isEqualTo(250);
    }

    @Test
    public void givenAsset_whenGetOldPriceTooLongAgo_thenReturnEmpty() {
        Asset asset = Asset.builder()
                .prices(List.of(
                        Price.builder()
                                .price(100)
                                .setDatetime(ZonedDateTime.now())
                                .build(),
                        Price.builder()
                                .price(50)
                                .setDatetime(ZonedDateTime.now().minusDays(1))
                                .build(),
                        Price.builder()
                                .price(250)
                                .setDatetime(ZonedDateTime.now().minusWeeks(1))
                                .build()))
                .build();

        Optional<Long> actualOldPrice = asset.getOldPrice(ZonedDateTime.now().minusYears(1));

        assertThat(actualOldPrice).isEmpty();
    }

    @Test
    public void givenHighRiskAsset_whenGetRiskType_returnHighRiskType() {
        Asset asset = Asset.builder()
                .releaseDatetime(ZonedDateTime.now())
                .company(Company.builder()
                        .foundationDatetime(ZonedDateTime.now())
                        .revenue(0)
                        .profit(-4_000_000_000L)
                        .staff(0)
                        .build())
                .interest(30.0)
                .operator(Operator.builder()
                        .inclusionDatetime(ZonedDateTime.now())
                        .build())
                .build();

        RiskType actualRiskType = asset.getRiskType();

        assertThat(actualRiskType).isEqualTo(RiskType.HIGH);
    }

    @Test
    public void givenLowRiskAsset_whenGetRiskType_returnLowRiskType() {
        Asset asset = Asset.builder()
                .releaseDatetime(ZonedDateTime.now().minusYears(30))
                .company(Company.builder()
                        .foundationDatetime(ZonedDateTime.now().minusYears(30))
                        .revenue(100_000_000_000_000L)
                        .profit(100_000_000_000_000L)
                        .staff(1000)
                        .build())
                .interest(5.0)
                .operator(Operator.builder()
                        .inclusionDatetime(ZonedDateTime.now().minusYears(30))
                        .build())
                .build();

        RiskType actualRiskType = asset.getRiskType();

        assertThat(actualRiskType).isEqualTo(RiskType.LOW);
    }
}