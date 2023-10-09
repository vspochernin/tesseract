package ru.tesseract.settings.ui

import androidx.lifecycle.ViewModel
import org.koin.core.annotation.Factory
import ru.tesseract.LoginState

@Factory
class SettingsViewModel(
    private val loginState: LoginState,
) : ViewModel() {
    fun onLogOut() {
        loginState.isLoggedIn = false
    }
}
