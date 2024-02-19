package ru.spbstu.tesseract.exception;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(MockitoExtension.class)
public class ExceptionApiHandlerTest {

    @InjectMocks
    private ExceptionApiHandler underTest;

    @Mock
    private TesseractException tesseractException;

    @Mock
    private BadCredentialsException badCredentialsException;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;
    private static HttpMessageNotReadableException httpMessageNotReadableException;
    private static NumberFormatException numberFormatException;
    private static NoSuchElementException noSuchElementException;
    private static ExceptionApiHandler exceptionApiHandler;

    @BeforeAll
    public static void setUp() {
        httpMessageNotReadableException = new HttpMessageNotReadableException("Test HttpMessageNotReadableException");
        numberFormatException = new NumberFormatException("Test NumberFormatException");
        noSuchElementException = new NoSuchElementException("Test NoSuchElementException");

        exceptionApiHandler = new ExceptionApiHandler();
    }

    @Test
    public void givenBadCredentialsException_whenHandled_thenReturnExpectedErrorMessage() {
        TesseractErrorType errorType = TesseractErrorType.BAD_CREDENTIALS;
        ErrorMessage expectedMessage = ErrorMessage.fromErrorTypeWithAdditionalInfo(errorType, badCredentialsException.toString());

        ResponseEntity<ErrorMessage> response = underTest.badCredentialsException(badCredentialsException);

        assertEquals(errorType.getHttpStatus(), response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
    }

    @Test
    public void givenMethodArgumentNotValidException_whenHandled_thenReturnExpectedErrorMessage() {
        TesseractErrorType errorType = TesseractErrorType.BAD_REQUEST_BODY;
        ErrorMessage expectedMessage = ErrorMessage.fromErrorTypeWithAdditionalInfo(errorType, methodArgumentNotValidException.toString());

        ResponseEntity<ErrorMessage> response = underTest.methodArgumentNotValidException(methodArgumentNotValidException);

        assertEquals(errorType.getHttpStatus(), response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
    }

    @Test
    public void givenHttpMessageNotReadableException_whenHandled_thenErrorMessageIsReturned() {
        ResponseEntity<ErrorMessage> result = exceptionApiHandler.httpMessageNotReadableException(httpMessageNotReadableException);
        assertEquals(TesseractErrorType.BAD_REQUEST_BODY.getHttpStatus(), result.getStatusCode());
        assertEquals(ErrorMessage.fromErrorTypeWithAdditionalInfo(TesseractErrorType.BAD_REQUEST_BODY, httpMessageNotReadableException.toString()), result.getBody());
    }

    @Test
    public void givenNumberFormatException_whenHandled_thenErrorMessageIsReturned() {
        ResponseEntity<ErrorMessage> result = exceptionApiHandler.numberFormatException(numberFormatException);
        assertEquals(TesseractErrorType.BAD_REQUEST_BODY.getHttpStatus(), result.getStatusCode());
        assertEquals(ErrorMessage.fromErrorTypeWithAdditionalInfo(TesseractErrorType.BAD_REQUEST_BODY, numberFormatException.toString()), result.getBody());
    }

    @Test
    public void givenNoSuchElementException_whenHandled_thenErrorMessageIsReturned() {
        ResponseEntity<ErrorMessage> result = exceptionApiHandler.noSuchElementException(noSuchElementException);
        assertEquals(TesseractErrorType.NOT_FOUND.getHttpStatus(), result.getStatusCode());
        assertEquals(ErrorMessage.fromErrorTypeWithAdditionalInfo(TesseractErrorType.NOT_FOUND, noSuchElementException.toString()), result.getBody());
    }



}