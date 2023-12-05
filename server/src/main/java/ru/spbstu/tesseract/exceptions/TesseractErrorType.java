package ru.spbstu.tesseract.exceptions;

import org.springframework.http.HttpStatus;

public enum TesseractErrorType {

    UNEXPECTED_ERROR(0, "Непредвиденная ошибка", HttpStatus.INTERNAL_SERVER_ERROR),
    LOGIN_EXISTS(1, "Пользователь с таким логином уже существует", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS(2, "Пользователь с таким email уже существует", HttpStatus.BAD_REQUEST),
    INCORRECT_LOGIN(3, "Некорректный логин", HttpStatus.BAD_REQUEST),
    INCORRECT_PASSWORD(4, "Некорректный пароль", HttpStatus.BAD_REQUEST),
    BAD_CREDENTIALS(5, "Пользователь с таким логином или паролем не найден", HttpStatus.UNAUTHORIZED),
    BAD_REQUEST_BODY(6, "Некорректное тело запроса", HttpStatus.BAD_REQUEST),
    NOT_FOUND(7, "Запрашиваемый элемент не найден", HttpStatus.NOT_FOUND),
    ;

    private final int id;
    private final String description;
    private final HttpStatus httpStatus;

    TesseractErrorType(int id, String description, HttpStatus httpStatus) {
        this.id = id;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
