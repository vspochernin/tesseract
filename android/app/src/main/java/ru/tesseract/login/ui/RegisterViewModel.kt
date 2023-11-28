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

@Factory
class RegisterViewModel(
    private val registerApi: RegisterApi,
) : ViewModel() {
    var login by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var isRegistering by mutableStateOf(false)
    val isValid by derivedStateOf {
        !isRegistering &&
                login.isNotEmpty() &&
                email.isNotEmpty() &&
                password.isNotEmpty() &&
                password == confirmPassword
    }

    fun onRegister(dismiss: () -> Unit) = viewModelScope.launch {
        if (isValid) {
            isRegistering = true
            registerApi.register(login, email, password).onSuccess {
                dismiss()
            }
            isRegistering = false
        }
    }
}
