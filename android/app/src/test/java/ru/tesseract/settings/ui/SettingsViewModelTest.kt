package ru.tesseract.settings.ui

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Test
import ru.tesseract.LoginMethod
import ru.tesseract.LoginState
import ru.tesseract.settings.data.Settings
import ru.tesseract.ui.theme.ThemeSetting

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {
    @Test
    fun `SettingsViewModel should reset token`() {
        val loginState = mockk<LoginState>()
        every { loginState.loginMethod } returns flowOf(LoginMethod.Tesseract)
        coEvery { loginState.resetToken() } returns Unit
        val settings = mockk<Settings>()
        val viewModel = SettingsViewModel(loginState, settings)
        Dispatchers.setMain(Dispatchers.Default)
        runTest {
            viewModel.onLogOut().join()
        }
        coVerify(exactly = 1) { loginState.resetToken() }
        verify { loginState.loginMethod }
        confirmVerified(loginState)
    }

    @Test
    fun `SettingsViewModel should change theme`() {
        val loginState = mockk<LoginState>()
        every { loginState.loginMethod } returns flowOf(LoginMethod.Tesseract)
        val settings = mockk<Settings>()
        coEvery { settings.setTheme(any()) } returns Unit
        val viewModel = SettingsViewModel(loginState, settings)
        Dispatchers.setMain(Dispatchers.Default)
        runTest {
            viewModel.onSetTheme(ThemeSetting.Dark).join()
        }
        verify { loginState.loginMethod }
        coVerify(exactly = 1) { settings.setTheme(ThemeSetting.Dark) }
        confirmVerified(settings)
        confirmVerified(loginState)
    }
}
