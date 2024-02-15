package ru.tesseract.settings.api

import io.ktor.client.request.setBody
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single
import ru.tesseract.KoverIgnore
import ru.tesseract.api.ApiClient
import ru.tesseract.api.ApiResponse

@Serializable
@KoverIgnore
private class PasswordRequest(
    val oldPassword: String,
    val newPassword: String,
)

@Single
@KoverIgnore
class PasswordApi(
    private val client: ApiClient,
) {
    suspend fun change(oldPassword: String, newPassword: String): ApiResponse<Unit> =
        client.put("password") { setBody(PasswordRequest(oldPassword, newPassword)) }
}
