package ru.spbstu.tesseract.exception;

public class IncorrectLoginException extends TesseractException {

    @Override
    public TesseractErrorType getErrorType() {
        return TesseractErrorType.INVALID_LOGIN;
    }
}
