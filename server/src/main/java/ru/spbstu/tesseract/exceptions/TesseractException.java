package ru.spbstu.tesseract.exceptions;

public class TesseractException extends RuntimeException {

    public TesseractErrorType getErrorType() {
        return TesseractErrorType.UNEXPECTED_ERROR;
    }
}
