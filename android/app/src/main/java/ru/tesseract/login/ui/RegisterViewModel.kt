package ru.tesseract.login.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import ru.tesseract.api.onFailure
import ru.tesseract.api.onSuccess
import ru.tesseract.login.api.RegisterApi
import ru.tesseract.ui.Validation

@Factory
class RegisterViewModel(
    private val registerApi: RegisterApi,
) : ViewModel() {
    var login by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var isRegistering by mutableStateOf(false)
    val isRegisterButtonEnabled by derivedStateOf {
        !isRegistering &&
                login.isNotEmpty() &&
                email.isNotEmpty() &&
                password.isNotEmpty() &&
                confirmPassword.isNotEmpty()
    }

    private var allowLoginError by mutableStateOf(false)
    private var allowEmailError by mutableStateOf(false)
    private var allowPasswordError by mutableStateOf(false)
    private var allowConfirmPasswordError by mutableStateOf(false)

    private val isLoginValid by derivedStateOf { Validation.isLoginValid(login) }
    private val isEmailValid by derivedStateOf { Validation.isEmailValid(email) }
    private val isPasswordValid by derivedStateOf { Validation.isPasswordValid(password) }
    private val isConfirmPasswordValid by derivedStateOf {
        Validation.isConfirmPasswordValid(password, confirmPassword)
    }

    val displayLoginError by derivedStateOf { !isLoginValid && allowLoginError }
    val displayEmailError by derivedStateOf { !isEmailValid && allowEmailError }
    val displayPasswordError by derivedStateOf { !isPasswordValid && allowPasswordError }
    val displayConfirmPasswordError by derivedStateOf { !isConfirmPasswordValid && allowConfirmPasswordError }

    fun onRegister(dismiss: () -> Unit) = viewModelScope.launch {
        allowLoginError = true
        allowEmailError = true
        allowPasswordError = true
        allowConfirmPasswordError = true
        if (isLoginValid && isEmailValid && isPasswordValid && isConfirmPasswordValid) {
            isRegistering = true
            registerApi.register(login, email, password).onSuccess {
                dismiss()
            }.onFailure {
                isRegistering = false
            }
        }
    }
}
