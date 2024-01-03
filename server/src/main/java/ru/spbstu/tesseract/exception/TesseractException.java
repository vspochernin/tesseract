package ru.spbstu.tesseract.exception;

public class TesseractException extends RuntimeException {

    private static final String DEFAULT_ADDITIONAL_INFO = "";

    private final TesseractErrorType errorType;
    private final String additionalInfo;

    public TesseractException(TesseractErrorType errorType) {
        this.errorType = errorType;
        this.additionalInfo = DEFAULT_ADDITIONAL_INFO;
    }

    public TesseractException(TesseractErrorType errorType, String additionalInfo) {
        this.errorType = errorType;
        this.additionalInfo = additionalInfo;
    }

    public TesseractErrorType getErrorType() {
        return errorType;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}
