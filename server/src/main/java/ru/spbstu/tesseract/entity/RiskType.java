package ru.spbstu.tesseract.entity;

import ru.spbstu.tesseract.exception.TesseractErrorType;
import ru.spbstu.tesseract.exception.TesseractException;

public enum RiskType {

    HIGH,
    MIDDLE,
    LOW,
    COMBINED;

    public static RiskType getById(int id) {
        for (RiskType riskType : values()) {
            if (riskType.ordinal() == id) {
                return riskType;
            }
        }

        throw new TesseractException(TesseractErrorType.INVALID_RISK_TYPE);
    }
}
