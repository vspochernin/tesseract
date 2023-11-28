package ru.tesseract.login.api

import io.ktor.client.request.setBody
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single
import ru.tesseract.api.ApiClient
import ru.tesseract.api.ApiResponse

@Serializable
private class RegisterRequest(
    val login: String,
    val email: String,
    val password: String,
)

@Single
class RegisterApi(private val client: ApiClient) {
    suspend fun register(login: String, email: String, password: String): ApiResponse<Unit> =
        client.post("/users") { setBody(RegisterRequest(login, email, password)) }
}
