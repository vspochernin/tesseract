package ru.spbstu.tesseract.entity;

public enum RiskType {

    HIGH,
    MIDDLE,
    LOW,
    COMBINED;

    public static RiskType getById(Integer id) {
        for (RiskType riskType : values()) {
            if (riskType.ordinal() == id) {
                return riskType;
            }
        }

        throw new IllegalArgumentException();
    }
}
