package ru.tesseract.login.ui

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.tesseract.LoginMethod
import ru.tesseract.LoginState
import ru.tesseract.api.ApiErrorType
import ru.tesseract.api.ApiResponse
import ru.tesseract.login.api.LoginApi
import ru.tesseract.login.api.LoginResponse

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    @Test
    fun `LoginViewModel should not allow login with empty fields`() {
        val loginState = mockk<LoginState>(relaxed = true)
        val loginApi = mockk<LoginApi>()
        val viewModel = LoginViewModel(loginState, loginApi)
        assertFalse(viewModel.isSignInEnabled)
        viewModel.login.value = "login"
        assertFalse(viewModel.isSignInEnabled)
        viewModel.password.value = "password"
        assertTrue(viewModel.isSignInEnabled)
    }

    @Test
    fun `LoginViewModel should set token on success`() {
        val loginState = mockk<LoginState>(relaxed = true)
        val loginApi = mockk<LoginApi>()
        coEvery { loginApi.login(any(), any()) } returns ApiResponse.Success(LoginResponse("abc"))
        val viewModel = LoginViewModel(loginState, loginApi)
        Dispatchers.setMain(Dispatchers.Default)
        runTest {
            viewModel.onLogin().join()
        }
        coVerify { loginState.setToken("abc", LoginMethod.Tesseract) }
        confirmVerified(loginState)
    }

    @Test
    fun `LoginViewModel should set Google token on success`() {
        val loginState = mockk<LoginState>(relaxed = true)
        val loginApi = mockk<LoginApi>()
        coEvery { loginApi.loginWithGoogle(any()) } returns ApiResponse.Success(LoginResponse("abc"))
        val viewModel = LoginViewModel(loginState, loginApi)
        Dispatchers.setMain(Dispatchers.Default)
        runTest {
            viewModel.onGoogleLogin("google").join()
        }
        coVerify { loginState.setToken("abc", LoginMethod.Google) }
        confirmVerified(loginState)
    }

    @Test
    fun `LoginViewModel should not set token on failure`() {
        val loginState = mockk<LoginState>(relaxed = true)
        val loginApi = mockk<LoginApi>()
        coEvery { loginApi.login(any(), any()) } returns ApiResponse.ApiError(ApiErrorType.BadCredentials)
        val viewModel = LoginViewModel(loginState, loginApi)
        Dispatchers.setMain(Dispatchers.Default)
        runTest {
            viewModel.onLogin().join()
        }
        coVerify(exactly = 0) { loginState.setToken(any(), any()) }
        confirmVerified(loginState)
    }
}
