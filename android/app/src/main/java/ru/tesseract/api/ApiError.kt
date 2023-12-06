package ru.tesseract.api

import androidx.annotation.StringRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tesseract.R

@Serializable
enum class ApiErrorType(@StringRes val message: Int) {
    @SerialName("UNEXPECTED_ERROR")
    UnexpectedError(R.string.error_unexpected_error),

    @SerialName("LOGIN_EXISTS")
    LoginExists(R.string.error_login_exists),

    @SerialName("EMAIL_EXISTS")
    EmailExists(R.string.error_email_exists),

    @SerialName("INCORRECT_LOGIN")
    IncorrectLogin(R.string.error_incorrect_login),

    @SerialName("INCORRECT_PASSWORD")
    IncorrectPassword(R.string.error_incorrect_password),

    @SerialName("BAD_CREDENTIALS")
    BadCredentials(R.string.error_bad_credentials),

    @SerialName("BAD_REQUEST_BODY")
    BadRequestBody(R.string.error_bad_request_body),

    @SerialName("NOT_FOUND")
    NotFound(R.string.error_not_found),

    @SerialName("PASSWORD_DOES_NOT_MATCH")
    PasswordDoesNotMatch(R.string.error_password_does_not_match),

    @SerialName("TOO_BIG_AMOUNT")
    TooBigAmount(R.string.error_too_big_amount),

    @SerialName("NO_ASSETS_WITH_SUCH_RISK_TYPE")
    NoAssetsWithSuchRiskType(R.string.error_no_assets_with_such_risk),

    @SerialName("TOO_LITTLE_AMOUNT")
    TooLittleAmount(R.string.error_too_little_amount),
}
