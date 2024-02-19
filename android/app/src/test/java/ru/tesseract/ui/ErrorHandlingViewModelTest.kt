package ru.tesseract.ui

import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.tesseract.api.ApiClient
import ru.tesseract.api.ApiErrorType
import ru.tesseract.api.ApiResponse

class ErrorHandlingViewModelTest {
    @Test
    fun `ErrorHandlingViewModel should relay errors`() = runTest {
        val exception = RuntimeException()
        val apiErrors = MutableSharedFlow<ApiErrorType>(replay = 1)
        apiErrors.tryEmit(ApiErrorType.UnexpectedError)
        val networkErrors = MutableSharedFlow<ApiResponse.NetworkError>(replay = 1)
        networkErrors.tryEmit(ApiResponse.NetworkError(exception))
        val client = mockk<ApiClient>()
        every { client.apiErrors } returns apiErrors
        every { client.networkErrors } returns networkErrors
        val viewModel = ErrorHandlingViewModel(client)
        viewModel.networkErrors.test {
            assertEquals(exception, awaitItem().exception)
        }
        viewModel.apiErrors.test {
            assertEquals(ApiErrorType.UnexpectedError, awaitItem())
        }
    }
}
