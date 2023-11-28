package ru.tesseract.login.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import ru.tesseract.LoginState
import ru.tesseract.api.ApiErrorType
import ru.tesseract.api.onApiError
import ru.tesseract.api.onSuccess
import ru.tesseract.login.api.LoginApi

@Factory
class LoginViewModel(
    private val loginState: LoginState,
    private val loginApi: LoginApi,
) : ViewModel() {
    val login = mutableStateOf("")
    val password = mutableStateOf("")
    var isLoggingIn by mutableStateOf(false)
    val isSignInEnabled by derivedStateOf { login.value.isNotEmpty() && password.value.isNotEmpty() && !isLoggingIn }

    fun onLogin() = viewModelScope.launch {
        isLoggingIn = true
        loginApi.login(login.value, password.value).onSuccess { response ->
            loginState.token = response.token
        }.onApiError { error ->
            if (error == ApiErrorType.BadCredentials) {
                password.value = ""
            }
        }
        isLoggingIn = false
    }
}
