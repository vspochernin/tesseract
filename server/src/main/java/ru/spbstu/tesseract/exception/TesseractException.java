package ru.spbstu.tesseract.exception;

public class TesseractException extends RuntimeException {

    private final String additionalInfo;

    public TesseractException() {
        this.additionalInfo = "There is no additional info";
    }

    public TesseractException(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public TesseractErrorType getErrorType() {
        return TesseractErrorType.UNEXPECTED_ERROR;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}
