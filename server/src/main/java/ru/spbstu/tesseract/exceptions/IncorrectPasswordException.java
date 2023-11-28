package ru.spbstu.tesseract.exceptions;

public class IncorrectPasswordException extends TesseractException {

    @Override
    public TesseractErrorType getErrorType() {
        return TesseractErrorType.INCORRECT_PASSWORD;
    }
}
