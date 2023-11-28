package ru.spbstu.tesseract.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(TesseractException.class)
    public ResponseEntity<ErrorMessage> tesseractException(TesseractException exception) {
        TesseractErrorType errorType = exception.getErrorType();
        return ResponseEntity
                .status(errorType.getHttpStatus())
                .body(ErrorMessage.fromErrorType(errorType));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> badCredentialsException(BadCredentialsException exception) {
        TesseractErrorType errorType = TesseractErrorType.BAD_CREDENTIALS;
        return ResponseEntity
                .status(errorType.getHttpStatus())
                .body(ErrorMessage.fromErrorTypeWithAdditionalInfo(errorType, exception.toString()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> notValid(MethodArgumentNotValidException ex) {
        TesseractErrorType errorType = TesseractErrorType.BAD_REQUEST_BODY;
        return ResponseEntity
                .status(errorType.getHttpStatus())
                .body(ErrorMessage.fromErrorTypeWithAdditionalInfo(errorType, ex.toString()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> notValid(HttpMessageNotReadableException ex) {
        TesseractErrorType errorType = TesseractErrorType.BAD_REQUEST_BODY;
        return ResponseEntity
                .status(errorType.getHttpStatus())
                .body(ErrorMessage.fromErrorTypeWithAdditionalInfo(errorType, ex.toString()));
    }
}
