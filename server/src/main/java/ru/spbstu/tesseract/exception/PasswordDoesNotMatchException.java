package ru.spbstu.tesseract.exception;

public class PasswordDoesNotMatchException extends TesseractException {


    @Override
    public TesseractErrorType getErrorType() {
        return TesseractErrorType.PASSWORD_DOES_NOT_MATCH;
    }
}
