package ru.tesseract.login.ui

import androidx.lifecycle.ViewModel
import org.koin.core.annotation.Factory
import ru.tesseract.LoginState

@Factory
class LoginViewModel(
    private val loginState: LoginState,
) : ViewModel() {
    fun onLogin() {
        loginState.isLoggedIn = true
    }
}
