package ru.spbstu.tesseract.exceptions;

public class IncorrectLoginException extends TesseractException {

    @Override
    public TesseractErrorType getErrorType() {
        return TesseractErrorType.INCORRECT_LOGIN;
    }
}
