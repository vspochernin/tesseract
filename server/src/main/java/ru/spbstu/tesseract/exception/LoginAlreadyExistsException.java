package ru.spbstu.tesseract.exception;

public class LoginAlreadyExistsException extends TesseractException {

    @Override
    public TesseractErrorType getErrorType() {
        return TesseractErrorType.LOGIN_EXISTS;
    }
}
