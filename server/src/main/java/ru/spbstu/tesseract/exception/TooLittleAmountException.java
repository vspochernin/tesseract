package ru.spbstu.tesseract.exception;

public class TooLittleAmountException extends TesseractException {

    @Override
    public TesseractErrorType getErrorType() {
        return TesseractErrorType.TOO_LITTLE_AMOUNT;
    }
}
