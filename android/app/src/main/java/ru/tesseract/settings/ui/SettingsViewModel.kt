package ru.tesseract.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import ru.tesseract.LoginState

@Factory
class SettingsViewModel(
    private val loginState: LoginState,
) : ViewModel() {
    fun onLogOut() = viewModelScope.launch {
        loginState.resetToken()
    }
}
