package ru.spbstu.tesseract.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import ru.spbstu.tesseract.entity.Asset;
import ru.spbstu.tesseract.entity.Diversification;
import ru.spbstu.tesseract.entity.DiversificationAsset;
import ru.spbstu.tesseract.entity.Price;
import ru.spbstu.tesseract.entity.RiskType;

import static org.assertj.core.api.Assertions.assertThat;

class DiversificationShortDtoTest {

    @Test
    public void givenDiversification_whenFromDiversification_thenReturnCorrectDiversificationShortDto() {
        ZonedDateTime now = ZonedDateTime.now();
        Diversification diversification = new Diversification(
                null,
                now,
                RiskType.HIGH,
                500,
                List.of(new DiversificationAsset(
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
        diversification.setId(1);
        DiversificationShortDto actualDiversificationShortDto =
                DiversificationShortDto.fromDiversification(diversification);

        DiversificationShortDto expectedDiversificationShortDto = DiversificationShortDto.builder()
                .diversificationId(1)
                .createDatetime(now.toString())
                .riskTypeId(RiskType.HIGH.ordinal())
                .currentAmount(1000)
                .amountDiff(500)
                .build();
        assertThat(actualDiversificationShortDto).isEqualTo(expectedDiversificationShortDto);
    }

}
