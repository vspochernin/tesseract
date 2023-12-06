package ru.spbstu.tesseract.exception;

public class TesseractException extends RuntimeException {

    public TesseractErrorType getErrorType() {
        return TesseractErrorType.UNEXPECTED_ERROR;
    }
}
