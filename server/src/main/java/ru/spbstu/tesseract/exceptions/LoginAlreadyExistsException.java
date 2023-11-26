package ru.spbstu.tesseract.exceptions;

public class LoginAlreadyExistsException extends TesseractException {

    @Override
    public TesseractErrorType getErrorType() {
        return TesseractErrorType.LOGIN_EXISTS;
    }
}
