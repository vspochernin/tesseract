package ru.spbstu.tesseract.exception;

public class EmailAlreadyExistsException extends TesseractException {

    @Override
    public TesseractErrorType getErrorType() {
        return TesseractErrorType.EMAIL_EXISTS;
    }
}
