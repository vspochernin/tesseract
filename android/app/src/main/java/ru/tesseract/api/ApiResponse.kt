package ru.tesseract.api

import kotlinx.serialization.Serializable

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    @Serializable
    data class ApiError(val errorType: ApiErrorType) : ApiResponse<Nothing>()
    data class NetworkError(val exception: Exception) : ApiResponse<Nothing>()
}

inline fun <T> ApiResponse<T>.onSuccess(body: (T) -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Success<T>) body(data)
    return this
}

inline fun <T> ApiResponse<T>.onApiError(body: (ApiErrorType) -> Unit): ApiResponse<T> {
    if (this is ApiResponse.ApiError) body(errorType)
    return this
}

inline fun <T> ApiResponse<T>.onFailure(body: () -> Unit): ApiResponse<T> {
    if (this !is ApiResponse.Success) body()
    return this
}
