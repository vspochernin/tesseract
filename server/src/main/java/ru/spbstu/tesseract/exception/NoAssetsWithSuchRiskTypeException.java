package ru.spbstu.tesseract.exception;

public class NoAssetsWithSuchRiskTypeException extends TesseractException {

    @Override
    public TesseractErrorType getErrorType() {
        return TesseractErrorType.NO_ASSETS_WITH_SUCH_RISK_TYPE;
    }
}
