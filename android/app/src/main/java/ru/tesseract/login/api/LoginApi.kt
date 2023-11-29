package ru.tesseract.login.api

import io.ktor.client.request.setBody
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single
import ru.tesseract.api.ApiClient
import ru.tesseract.api.ApiResponse

@Serializable
class LoginResponse(
    val token: String,
)

@Serializable
private class LoginRequest(
    val login: String,
    val password: String,
)

@Single
class LoginApi(private val client: ApiClient) {
    suspend fun login(login: String, password: String): ApiResponse<LoginResponse> =
        client.post("/login/tesseract") { setBody(LoginRequest(login, password)) }
}
