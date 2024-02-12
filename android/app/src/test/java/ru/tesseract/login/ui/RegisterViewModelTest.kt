package ru.tesseract.login.ui

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Test
import ru.tesseract.api.ApiErrorType
import ru.tesseract.api.ApiResponse
import ru.tesseract.login.api.RegisterApi
import ru.tesseract.login.api.RegisterResponse

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {
    @Test
    fun `RegisterViewModel should register user`() {
        val registerApi = mockk<RegisterApi>()
        val dismiss = mockk<() -> Unit>()
        every { dismiss.invoke() } returns Unit
        coEvery { registerApi.register(any(), any(), any()) } returns
                ApiResponse.Success(RegisterResponse("abc"))
        val viewModel = RegisterViewModel(registerApi)
        viewModel.login = "login"
        viewModel.password = "password123"
        viewModel.confirmPassword = "password123"
        viewModel.email = "email@gmail.com"
        Dispatchers.setMain(Dispatchers.Default)
        runTest {
            viewModel.onRegister(dismiss).join()
        }
        coVerify(exactly = 1) { registerApi.register("login", "email@gmail.com", "password123") }
        verify(exactly = 1) { dismiss() }
        confirmVerified(dismiss)
        confirmVerified(registerApi)
    }

    @Test
    fun `RegisterViewModel should not dismiss on error`() {
        val registerApi = mockk<RegisterApi>()
        val dismiss = mockk<() -> Unit>()
        every { dismiss.invoke() } returns Unit
        coEvery { registerApi.register(any(), any(), any()) } returns
                ApiResponse.ApiError(ApiErrorType.EmailExists)
        val viewModel = RegisterViewModel(registerApi)
        viewModel.login = "login"
        viewModel.password = "password123"
        viewModel.confirmPassword = "password123"
        viewModel.email = "email@gmail.com"
        Dispatchers.setMain(Dispatchers.Default)
        runTest {
            viewModel.onRegister(dismiss).join()
        }
        coVerify(exactly = 1) { registerApi.register("login", "email@gmail.com", "password123") }
        verify(exactly = 0) { dismiss() }
        confirmVerified(dismiss)
        confirmVerified(registerApi)
    }

    @Test
    fun `RegisterViewModel should not register invalid user`() {
        val registerApi = mockk<RegisterApi>()
        val dismiss = mockk<() -> Unit>()
        every { dismiss.invoke() } returns Unit
        coEvery { registerApi.register(any(), any(), any()) } returns
                ApiResponse.Success(RegisterResponse("abc"))
        val viewModel = RegisterViewModel(registerApi)
        viewModel.login = "login"
        viewModel.password = "password123"
        viewModel.confirmPassword = "password123"
        viewModel.email = "invalid"
        Dispatchers.setMain(Dispatchers.Default)
        runTest {
            viewModel.onRegister(dismiss).join()
        }
        coVerify(exactly = 0) { registerApi.register(any(), any(), any()) }
        verify(exactly = 0) { dismiss() }
        confirmVerified(dismiss)
        confirmVerified(registerApi)
    }

    @Test
    fun `RegisterViewModel should enable button correctly`() {
        val registerApi = mockk<RegisterApi>()
        val viewModel = RegisterViewModel(registerApi)
        assertFalse(viewModel.isRegisterButtonEnabled)
        viewModel.login = "login"
        assertFalse(viewModel.isRegisterButtonEnabled)
        viewModel.email = "email@gmail.com"
        assertFalse(viewModel.isRegisterButtonEnabled)
        viewModel.password = "password123"
        assertFalse(viewModel.isRegisterButtonEnabled)
        viewModel.confirmPassword = "password123"
        assertTrue(viewModel.isRegisterButtonEnabled)
        viewModel.isRegistering = true
        assertFalse(viewModel.isRegisterButtonEnabled)
    }
}
