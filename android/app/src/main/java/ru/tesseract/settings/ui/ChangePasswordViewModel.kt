package ru.tesseract.settings.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import ru.tesseract.api.ApiErrorType
import ru.tesseract.api.onApiError
import ru.tesseract.api.onSuccess
import ru.tesseract.settings.api.PasswordApi
import ru.tesseract.ui.Validation

@Factory
class ChangePasswordViewModel(
    private val passwordApi: PasswordApi,
) : ViewModel() {
    var oldPassword by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    private var allowError by mutableStateOf(false)
    private var isChangingPassword by mutableStateOf(false)
    val isButtonEnabled by derivedStateOf {
        !isChangingPassword &&
                oldPassword.isNotEmpty() &&
                password.isNotEmpty() &&
                confirmPassword.isNotEmpty()
    }
    var showSuccessDialog by mutableStateOf(false)

    private val isPasswordValid by derivedStateOf { Validation.isPasswordValid(password) }
    private val isConfirmPasswordValid by derivedStateOf {
        Validation.isConfirmPasswordValid(password, confirmPassword)
    }
    val displayPasswordError by derivedStateOf { !isPasswordValid && allowError }
    val displayConfirmPasswordError by derivedStateOf { !isConfirmPasswordValid && allowError }

    fun onChange() = viewModelScope.launch {
        allowError = true
        if (isPasswordValid && isConfirmPasswordValid) {
            isChangingPassword = true
            passwordApi.change(oldPassword, password).onSuccess {
                allowError = false
                oldPassword = ""
                password = ""
                confirmPassword = ""
                showSuccessDialog = true
            }.onApiError { error ->
                when (error) {
                    ApiErrorType.PasswordDoesNotMatch -> {
                        oldPassword = ""
                    }
                    ApiErrorType.IncorrectPassword -> {
                        password = ""
                        confirmPassword = ""
                    }
                    else -> {}
                }
            }
            isChangingPassword = false
        }
    }
}
