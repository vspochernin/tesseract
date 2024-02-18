package ru.spbstu.tesseract.dto;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.spbstu.tesseract.entity.Diversification;
import ru.spbstu.tesseract.entity.RiskType;

import static org.assertj.core.api.Assertions.assertThat;

class DiversificationShortDtoTest {

    /// НЕ РАБОТАЕТ ЗАПОЛНЕНИЕ diversificationAssetSet
    @Test
    public void givenDiversification_whenFromDiversification_thenReturnCorrectDiversificationShortDto() {
        Diversification diversification = Diversification.builder()
                .id(1)
                .createDatetime(ZonedDateTime.now())
                .riskType(RiskType.HIGH)
                .amount(500)
                .diversificationAssetSet(new HashSet<>())
                .build();
        Diversification mockedDiversification = Mockito.spy(diversification);

        DiversificationShortDto actualDiversificationShortDto = DiversificationShortDto.fromDiversification(mockedDiversification);

        DiversificationShortDto expectedDiversificationShortDto = DiversificationShortDto.builder()
                .diversificationId(1)
                .createDatetime(diversification.getCreateDatetime()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")))
                .riskTypeId(RiskType.HIGH.ordinal())
                .currentAmount(1000)
                .amountDiff(500)
                .build();
        assertThat(actualDiversificationShortDto).isEqualTo(expectedDiversificationShortDto);
    }

}
