package ru.tesseract.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.core.annotation.Single
import ru.tesseract.KoverIgnore
import ru.tesseract.LoginState
import ru.tesseract.settings.data.Settings

@Single
@KoverIgnore
class ApiClient(
    val httpClient: HttpClient,
    val loginState: LoginState,
    val settings: Settings,
) {
    val apiErrors = MutableSharedFlow<ApiErrorType>()
    val networkErrors = MutableSharedFlow<ApiResponse.NetworkError>()

    suspend inline fun <reified T> request(
        url: String,
        block: HttpRequestBuilder.() -> Unit,
    ): ApiResponse<T> {
        return try {
            val response = httpClient.request(settings.baseUrl.value + url) {
                loginState.token.value?.let {
                    header("Authorization", "Bearer $it")
                }
                block()
            }
            if (response.status.isSuccess()) {
                ApiResponse.Success(response.body())
            } else {
                response.body<ApiResponse.ApiError>().also { apiErrors.emit(it.errorType) }
            }
        } catch (e: Exception) {
            ApiResponse.NetworkError(e).also { networkErrors.emit(it) }
        }
    }

    suspend inline fun <reified T> post(
        url: String,
        block: HttpRequestBuilder.() -> Unit = {},
    ): ApiResponse<T> = request(url) {
        method = HttpMethod.Post
        block()
    }

    suspend inline fun <reified T> get(
        url: String,
        block: HttpRequestBuilder.() -> Unit = {},
    ): ApiResponse<T> = request(url) {
        method = HttpMethod.Get
        block()
    }

    suspend inline fun <reified T> put(
        url: String,
        block: HttpRequestBuilder.() -> Unit = {},
    ): ApiResponse<T> = request(url) {
        method = HttpMethod.Put
        block()
    }

    suspend inline fun <reified T> delete(
        url: String,
        block: HttpRequestBuilder.() -> Unit = {},
    ): ApiResponse<T> = request(url) {
        method = HttpMethod.Delete
        block()
    }
}
