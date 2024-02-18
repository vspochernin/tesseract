package ru.spbstu.tesseract.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TesseractExceptionTest {

    @Test
    public void givenUnexpectedTesseractException_whenGetErrorType_returnUnexpectedErrorType() {
        TesseractException tesseractException = new TesseractException(TesseractErrorType.UNEXPECTED_ERROR);

        TesseractErrorType actualErrorType = tesseractException.getErrorType();

        assertThat(actualErrorType).isEqualTo(TesseractErrorType.UNEXPECTED_ERROR);
    }

    @Test
    public void givenLoginExistsTesseractException_whenGetErrorType_returnLoginExistsErrorType() {
        TesseractException tesseractException = new TesseractException(TesseractErrorType.LOGIN_EXISTS);

        TesseractErrorType actualErrorType = tesseractException.getErrorType();

        assertThat(actualErrorType).isEqualTo(TesseractErrorType.LOGIN_EXISTS);
    }

    @Test
    public void givenEmailExistsTesseractException_whenGetErrorType_returnEmailExistsErrorType() {
        TesseractException tesseractException = new TesseractException(TesseractErrorType.EMAIL_EXISTS);

        TesseractErrorType actualErrorType = tesseractException.getErrorType();

        assertThat(actualErrorType).isEqualTo(TesseractErrorType.EMAIL_EXISTS);
    }

    @Test
    public void givenInvalidLoginTesseractException_whenGetErrorType_returnInvalidLoginErrorType() {
        TesseractException tesseractException = new TesseractException(TesseractErrorType.INVALID_LOGIN, "some_to_add");

        TesseractErrorType actualErrorType = tesseractException.getErrorType();

        assertThat(actualErrorType).isEqualTo(TesseractErrorType.INVALID_LOGIN);
    }

    @Test
    public void givenInvalidPasswordTesseractException_whenGetErrorType_returnInvalidPasswordErrorType() {
        TesseractException tesseractException = new TesseractException(TesseractErrorType.INVALID_PASSWORD);

        TesseractErrorType actualErrorType = tesseractException.getErrorType();

        assertThat(actualErrorType).isEqualTo(TesseractErrorType.INVALID_PASSWORD);
    }

    @Test
    public void givenInvalidEmailTesseractException_whenGetErrorType_returnInvalidEmailErrorType() {
        TesseractException tesseractException = new TesseractException(TesseractErrorType.INVALID_EMAIL, "some_to_add");

        TesseractErrorType actualErrorType = tesseractException.getErrorType();

        assertThat(actualErrorType).isEqualTo(TesseractErrorType.INVALID_EMAIL);
    }

    @Test
    public void givenBadCredentialsTesseractException_whenGetErrorType_returnBadCredentialsErrorType() {
        TesseractException tesseractException = new TesseractException(TesseractErrorType.BAD_CREDENTIALS);

        TesseractErrorType actualErrorType = tesseractException.getErrorType();

        assertThat(actualErrorType).isEqualTo(TesseractErrorType.BAD_CREDENTIALS);
    }

    @Test
    public void givenBadRequestBodyTesseractException_whenGetErrorType_returnBadRequestBodyErrorType() {
        TesseractException tesseractException = new TesseractException(TesseractErrorType.BAD_REQUEST_BODY);

        TesseractErrorType actualErrorType = tesseractException.getErrorType();

        assertThat(actualErrorType).isEqualTo(TesseractErrorType.BAD_REQUEST_BODY);
    }

    @Test
    public void givenNotFoundTesseractException_whenGetErrorType_returnNotFoundErrorType() {
        TesseractException tesseractException = new TesseractException(TesseractErrorType.NOT_FOUND);

        TesseractErrorType actualErrorType = tesseractException.getErrorType();

        assertThat(actualErrorType).isEqualTo(TesseractErrorType.NOT_FOUND);
    }

    @Test
    public void givenPasswordDoesNotMatchTesseractException_whenGetErrorType_returnPasswordDoesNotMatchErrorType() {
        TesseractException tesseractException = new TesseractException(TesseractErrorType.PASSWORD_DOES_NOT_MATCH);

        TesseractErrorType actualErrorType = tesseractException.getErrorType();

        assertThat(actualErrorType).isEqualTo(TesseractErrorType.PASSWORD_DOES_NOT_MATCH);
    }

    @Test
    public void givenTooBigAmountTesseractException_whenGetErrorType_returnTooBigAmountErrorType() {
        TesseractException tesseractException = new TesseractException(TesseractErrorType.TOO_BIG_AMOUNT);

        TesseractErrorType actualErrorType = tesseractException.getErrorType();

        assertThat(actualErrorType).isEqualTo(TesseractErrorType.TOO_BIG_AMOUNT);
    }

    @Test
    public void givenTooLittleAmountTesseractException_whenGetErrorType_returnTooLittleAmountErrorType() {
        TesseractException tesseractException = new TesseractException(TesseractErrorType.TOO_LITTLE_AMOUNT);

        TesseractErrorType actualErrorType = tesseractException.getErrorType();

        assertThat(actualErrorType).isEqualTo(TesseractErrorType.TOO_LITTLE_AMOUNT);
    }

    @Test
    public void givenNoAssetWithSuchRiskTypeTesseractException_whenGetErrorType_returnNoAssetWithSuchRiskTypeErrorType() {
        TesseractException tesseractException = new TesseractException(TesseractErrorType.NO_ASSETS_WITH_SUCH_RISK_TYPE)
                ;

        TesseractErrorType actualErrorType = tesseractException.getErrorType();

        assertThat(actualErrorType).isEqualTo(TesseractErrorType.NO_ASSETS_WITH_SUCH_RISK_TYPE);
    }

    @Test
    public void givenGoogleTokenCannotBeVerifiedTesseractException_whenGetErrorType_returnGoogleTokenCannotBeVerifiedErrorType() {
        TesseractException tesseractException = new TesseractException(TesseractErrorType.GOOGLE_TOKEN_CANNOT_BE_VERIFIED)
                ;

        TesseractErrorType actualErrorType = tesseractException.getErrorType();

        assertThat(actualErrorType).isEqualTo(TesseractErrorType.GOOGLE_TOKEN_CANNOT_BE_VERIFIED);
    }

    @Test
    public void givenTesseractExceptionWithAdditionalInfo_whenGetAdditionalInfo_returnAdditionalInfo() {
        TesseractException tesseractException = new TesseractException(TesseractErrorType.INVALID_LOGIN, "some_to_add");

        String actualAdditionalInfo = tesseractException.getAdditionalInfo();

        assertThat(actualAdditionalInfo).isEqualTo("some_to_add");
    }
}