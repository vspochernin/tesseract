package ru.spbstu.tesseract.exception;

public class IncorrectPasswordException extends TesseractException {

    @Override
    public TesseractErrorType getErrorType() {
        return TesseractErrorType.INVALID_PASSWORD;
    }
}
