package ru.tesseract.login.ui

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import ru.tesseract.LoginMethod
import ru.tesseract.LoginState
import ru.tesseract.api.ApiErrorType
import ru.tesseract.api.ApiResponse
import ru.tesseract.login.api.LoginApi
import ru.tesseract.login.api.LoginResponse
import ru.tesseract.settings.data.Settings
import ru.tesseract.util.MainDispatcherRule

class LoginViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `LoginViewModel should not allow login with empty fields`() {
        val loginState = mockk<LoginState>(relaxed = true)
        val loginApi = mockk<LoginApi>()
        val settings = mockk<Settings>(relaxed = true)
        val viewModel = LoginViewModel(loginState, loginApi, settings)
        assertFalse(viewModel.isSignInEnabled)
        viewModel.login.value = "login"
        assertFalse(viewModel.isSignInEnabled)
        viewModel.password.value = "password"
        assertTrue(viewModel.isSignInEnabled)
    }

    @Test
    fun `LoginViewModel should set token on success`() = runTest {
        val loginState = mockk<LoginState>(relaxed = true)
        val loginApi = mockk<LoginApi>()
        coEvery { loginApi.login(any(), any()) } returns ApiResponse.Success(LoginResponse("abc"))
        val settings = mockk<Settings>(relaxed = true)
        val viewModel = LoginViewModel(loginState, loginApi, settings)
        viewModel.onLogin().join()
        coVerify { loginState.setToken("abc", LoginMethod.Tesseract) }
        confirmVerified(loginState)
    }

    @Test
    fun `LoginViewModel should set Google token on success`() = runTest {
        val loginState = mockk<LoginState>(relaxed = true)
        val loginApi = mockk<LoginApi>()
        coEvery { loginApi.loginWithGoogle(any()) } returns ApiResponse.Success(LoginResponse("abc"))
        val settings = mockk<Settings>(relaxed = true)
        val viewModel = LoginViewModel(loginState, loginApi, settings)
        viewModel.onGoogleLogin("google").join()
        coVerify { loginState.setToken("abc", LoginMethod.Google) }
        confirmVerified(loginState)
    }

    @Test
    fun `LoginViewModel should not set token on failure`() = runTest {
        val loginState = mockk<LoginState>(relaxed = true)
        val loginApi = mockk<LoginApi>()
        coEvery { loginApi.login(any(), any()) } returns ApiResponse.ApiError(ApiErrorType.BadCredentials)
        val settings = mockk<Settings>(relaxed = true)
        val viewModel = LoginViewModel(loginState, loginApi, settings)
        viewModel.onLogin().join()
        coVerify(exactly = 0) { loginState.setToken(any(), any()) }
        confirmVerified(loginState)
    }
}
