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
                                .build(),
                        Price.builder()
                                .price(200)
                                .setDatetime(ZonedDateTime.now().minusDays(2))
                                .build(),
                        Price.builder()
                                .price(300)
                                .setDatetime(ZonedDateTime.now().minusDays(3))
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
                                .build(),
                        Price.builder()
                                .price(200)
                                .setDatetime(ZonedDateTime.now().minusDays(2))
                                .build(),
                        Price.builder()
                                .price(300)
                                .setDatetime(ZonedDateTime.now().minusDays(3))
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
                                .price(200)
                                .setDatetime(ZonedDateTime.now().minusDays(2))
                                .build(),
                        Price.builder()
                                .price(300)
                                .setDatetime(ZonedDateTime.now().minusDays(3))
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
                                .price(200)
                                .setDatetime(ZonedDateTime.now().minusDays(2))
                                .build(),
                        Price.builder()
                                .price(250)
                                .setDatetime(ZonedDateTime.now().minusDays(3))
                                .build(),
                        Price.builder()
                                .price(1000)
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
                                .price(200)
                                .setDatetime(ZonedDateTime.now().minusDays(2))
                                .build(),
                        Price.builder()
                                .price(250)
                                .setDatetime(ZonedDateTime.now().minusDays(3))
                                .build(),
                        Price.builder()
                                .price(1000)
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

    @Test
    public void givenHighRiskAssetWithMorePoints_whenGetRiskType_returnHighRiskType() {
        Asset asset = Asset.builder()
                .releaseDatetime(ZonedDateTime.now().minusDays(5))
                .company(Company.builder()
                        .foundationDatetime(ZonedDateTime.now().minusYears(1))
                        .revenue(10_000_000_000L)
                        .profit(-3_000_000_000L)
                        .staff(5)
                        .build())
                .interest(20.0)
                .operator(Operator.builder()
                        .inclusionDatetime(ZonedDateTime.now().minusDays(500))
                        .build())
                .build();

        RiskType actualRiskType = asset.getRiskType();

        assertThat(actualRiskType).isEqualTo(RiskType.HIGH);
    }

    @Test
    public void givenHighRiskAssetWithMiddlePoints_whenGetRiskType_returnHighRiskType() {
        Asset asset = Asset.builder()
                .releaseDatetime(ZonedDateTime.now().minusDays(30))
                .company(Company.builder()
                        .foundationDatetime(ZonedDateTime.now().minusYears(4))
                        .revenue(50_000_000_000L)
                        .profit(1_000_000_000L)
                        .staff(50)
                        .build())
                .interest(15.0)
                .operator(Operator.builder()
                        .inclusionDatetime(ZonedDateTime.now().minusDays(300))
                        .build())
                .build();

        RiskType actualRiskType = asset.getRiskType();

        assertThat(actualRiskType).isEqualTo(RiskType.HIGH);
    }

    @Test
    public void givenHighRiskAssetWithSmallPoints_whenGetRiskType_returnHighRiskType() {
        Asset asset = Asset.builder()
                .releaseDatetime(ZonedDateTime.now().minusDays(10))
                .company(Company.builder()
                        .foundationDatetime(ZonedDateTime.now().minusYears(2))
                        .revenue(5_000_000_000L)
                        .profit(-1_000_000_000L)
                        .staff(10)
                        .build())
                .interest(17.0)
                .operator(Operator.builder()
                        .inclusionDatetime(ZonedDateTime.now().minusDays(100))
                        .build())
                .build();

        RiskType actualRiskType = asset.getRiskType();

        assertThat(actualRiskType).isEqualTo(RiskType.HIGH);
    }

    @Test
    public void givenMiddleRiskAsset_whenGetRiskType_returnMiddleRiskType() {
        Asset asset = Asset.builder()
                .releaseDatetime(ZonedDateTime.now().minusDays(90))
                .company(Company.builder()
                        .foundationDatetime(ZonedDateTime.now().minusYears(7))
                        .revenue(100_000_000_000L)
                        .profit(0L)
                        .staff(200)
                        .build())
                .interest(11.0)
                .operator(Operator.builder()
                        .inclusionDatetime(ZonedDateTime.now().minusDays(60))
                        .build())
                .build();

        RiskType actualRiskType = asset.getRiskType();

        assertThat(actualRiskType).isEqualTo(RiskType.MIDDLE);
    }

}