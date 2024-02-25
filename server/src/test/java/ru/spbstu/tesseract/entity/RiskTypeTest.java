package ru.spbstu.tesseract.entity;

import org.junit.jupiter.api.Test;
import ru.spbstu.tesseract.exception.TesseractException;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RiskTypeTest {

    @Test
    public void givenIdHighRiskType_whenGetRiskTypeById_returnHighRiskType() {
        int riskTypeId = 0;

        RiskType actualRiskType = RiskType.getById(riskTypeId);

        assertThat(actualRiskType).isEqualTo(RiskType.HIGH);
    }

    @Test
    public void givenIdMiddleRiskType_whenGetRiskTypeById_returnMiddleRiskType() {
        int riskTypeId = 1;

        RiskType actualRiskType = RiskType.getById(riskTypeId);

        assertThat(actualRiskType).isEqualTo(RiskType.MIDDLE);
    }

    @Test
    public void givenIdLowRiskType_whenGetRiskTypeById_returnLowRiskType() {
        int riskTypeId = 2;

        RiskType actualRiskType = RiskType.getById(riskTypeId);

        assertThat(actualRiskType).isEqualTo(RiskType.LOW);
    }

    @Test
    public void givenIdCombinedRiskType_whenGetRiskTypeById_returnCombinedRiskType() {
        int riskTypeId = 3;

        RiskType actualRiskType = RiskType.getById(riskTypeId);

        assertThat(actualRiskType).isEqualTo(RiskType.COMBINED);
    }

    @Test
    public void givenNegativeIdRiskType_whenGetRiskTypeById_returnException() {
        int riskTypeId = -1;
        assertThrows(TesseractException.class, () -> RiskType.getById(riskTypeId));
    }

    @Test
    public void givenBigIdRiskType_whenGetRiskTypeById_returnException() {
        int riskTypeId = 5;
        assertThrows(TesseractException.class, () -> RiskType.getById(riskTypeId));
    }

}