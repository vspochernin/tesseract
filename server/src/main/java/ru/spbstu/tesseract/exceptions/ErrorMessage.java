package ru.spbstu.tesseract.exceptions;


public record ErrorMessage(
        int id,
        String description,
        String enumName) {
    public static ErrorMessage fromErrorType(TesseractErrorType errorType) {
        return new ErrorMessage(errorType.getId(), errorType.getDescription(), errorType.name());
    }
}
