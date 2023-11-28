package ru.tesseract.login.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import ru.tesseract.api.onSuccess
import ru.tesseract.login.api.RegisterApi

private val SpecialSymbols = setOf(
    '!', '@', '#', '$', '%', '&', '*', '(', ')', '-', '_', '+', '=',
    ';', ':', ',', '.', '/', '?', '\\', '|', '[', ']', '{', '}',
)

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

    var allowLoginError by mutableStateOf(false)
    var allowEmailError by mutableStateOf(false)
    var allowPasswordError by mutableStateOf(false)
    var allowConfirmPasswordError by mutableStateOf(false)

    private val isLoginValid by derivedStateOf {
        login.length in 3..16 && login.all { it.isLatinLetter() || it.isDigit() }
    }
    private val isEmailValid by derivedStateOf {
        '@' in email
    }
    private val isPasswordValid by derivedStateOf {
        password.length in 6..30 &&
                password.all { it.isLatinLetter() || it.isDigit() || it.isSpecialSymbol() } &&
                password.any { it.isLatinLetter() } &&
                password.any { it.isDigit() || it.isSpecialSymbol() }
    }
    private val isConfirmPasswordValid by derivedStateOf {
        password == confirmPassword
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
            }
            isRegistering = false
        }
    }

    private fun Char.isLatinLetter() = this in 'a'..'z' || this in 'A'..'Z'
    private fun Char.isSpecialSymbol() = this in SpecialSymbols
}
