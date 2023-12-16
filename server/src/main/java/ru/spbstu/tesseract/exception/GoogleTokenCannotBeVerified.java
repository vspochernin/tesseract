package ru.spbstu.tesseract.exception;

public class GoogleTokenCannotBeVerified extends TesseractException {

    public GoogleTokenCannotBeVerified() {
        super();
    }

    public GoogleTokenCannotBeVerified(String msg) {
        super(msg);
    }

    @Override
    public TesseractErrorType getErrorType() {
        return TesseractErrorType.GOOGLE_TOKEN_CANNOT_BE_VERIFIED;
    }
}
