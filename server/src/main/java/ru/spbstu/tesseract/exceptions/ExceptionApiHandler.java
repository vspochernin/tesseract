package ru.spbstu.tesseract.exceptions;

import org.springframework.http.ResponseEntity;
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
}
