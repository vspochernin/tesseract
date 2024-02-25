package ru.spbstu.tesseract.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TesseractErrorType {

    UNEXPECTED_ERROR(0, "Непредвиденная ошибка", HttpStatus.INTERNAL_SERVER_ERROR),
    LOGIN_EXISTS(1, "Пользователь с таким логином уже существует", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS(2, "Пользователь с таким email уже существует", HttpStatus.BAD_REQUEST),
    INVALID_LOGIN(3, "Некорректный логин", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(4, "Некорректный пароль", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(5, "Некорректный email", HttpStatus.BAD_REQUEST),
    BAD_CREDENTIALS(6, "Пользователь с таким логином или паролем не найден", HttpStatus.UNAUTHORIZED),
    BAD_REQUEST_BODY(7, "Некорректное тело запроса", HttpStatus.BAD_REQUEST),
    NOT_FOUND(8, "Запрашиваемый элемент не найден", HttpStatus.NOT_FOUND),
    PASSWORD_DOES_NOT_MATCH(9, "Новый пароль не совпадает со старым", HttpStatus.UNAUTHORIZED),
    TOO_BIG_AMOUNT(10, "Запрашиваемая сумма диверсификации превысила 10_000_000 рублей", HttpStatus.BAD_REQUEST),
    NO_ASSETS_WITH_SUCH_RISK_TYPE(
            11,
            "На данный момент отсутствуют активы с выбранным типом рискованности",
            HttpStatus.NOT_FOUND),
    TOO_LITTLE_AMOUNT(
            12,
            "Запрашиваемая сумма диверсификации меньше минимальной стоимости актива выбранной рискованности",
            HttpStatus.BAD_REQUEST),
    INVALID_RISK_TYPE(13, "Некорректный уровень рискованности", HttpStatus.BAD_REQUEST),
    GOOGLE_TOKEN_CANNOT_BE_VERIFIED(14, "Ошибка верификации Google токена", HttpStatus.BAD_REQUEST),
    ;

    private final int id;
    private final String description;
    private final HttpStatus httpStatus;

    TesseractErrorType(int id, String description, HttpStatus httpStatus) {
        this.id = id;
        this.description = description;
        this.httpStatus = httpStatus;
    }

}
