package ru.spbstu.tesseract.exceptions;

public class EmailAlreadyExistsException extends TesseractException {

    @Override
    public TesseractErrorType getErrorType() {
        return TesseractErrorType.EMAIL_EXISTS;
    }
}
