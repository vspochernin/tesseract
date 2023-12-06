package ru.spbstu.tesseract.exception;

public class TooBigAmountException extends TesseractException {

    @Override
    public TesseractErrorType getErrorType() {
        return TesseractErrorType.TOO_BIG_AMOUNT;
    }
}
