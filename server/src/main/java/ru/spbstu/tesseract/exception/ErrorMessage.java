package ru.spbstu.tesseract.exception;


public record ErrorMessage(
        int id,
        String description,
        String errorType,
        String additionalInfo) {

    public static ErrorMessage fromErrorType(TesseractErrorType errorType) {
        return new ErrorMessage(errorType.getId(), errorType.getDescription(), errorType.name(), "");
    }

    public static ErrorMessage fromErrorTypeWithAdditionalInfo(TesseractErrorType errorType, String additionalInfo) {
        return new ErrorMessage(errorType.getId(), errorType.getDescription(), errorType.name(), additionalInfo);
    }
}
