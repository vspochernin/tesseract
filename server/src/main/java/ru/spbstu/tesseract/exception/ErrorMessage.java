package ru.spbstu.tesseract.exception;

public record ErrorMessage(
        int id,
        String description,
        String errorType,
        String additionalInfo)
{

    public static ErrorMessage fromErrorTypeWithAdditionalInfo(TesseractErrorType errorType, String additionalInfo) {
        return new ErrorMessage(errorType.getId(), errorType.getDescription(), errorType.name(), additionalInfo);
    }

    public static ErrorMessage fromTesseractException(TesseractException exception) {
        TesseractErrorType errorType = exception.getErrorType();

        return new ErrorMessage(
                errorType.getId(),
                errorType.getDescription(),
                errorType.name(),
                exception.getAdditionalInfo());
    }
}
