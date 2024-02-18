package ru.spbstu.tesseract.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorMessageTest {

    @Test
    public void givenTesseractException_whenGetErrorMessageIdFromTesseractException_returnIdErrorMessage() {
        TesseractException tesseractException = new TesseractException(TesseractErrorType.INVALID_LOGIN, "some_to_add");
        ErrorMessage errorMessage = ErrorMessage.fromTesseractException(tesseractException);

        int actualIdErrorMessage = 3;

        assertThat(errorMessage.id()).isEqualTo(actualIdErrorMessage);
    }

    @Test
    public void givenTesseractExceptionWithAdditionalInfo_whenFromErrorTypeWithAdditionalInfo_returnAdditionalInfoErrorMessage() {
        ErrorMessage errorMessage = ErrorMessage.fromErrorTypeWithAdditionalInfo(TesseractErrorType.UNEXPECTED_ERROR,  "some_to_add");

        String actualIdAdditionalInfo = "some_to_add";

        assertThat(errorMessage.additionalInfo()).isEqualTo( actualIdAdditionalInfo);
    }
}