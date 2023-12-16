package ru.tesseract.settings.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import ru.tesseract.LoginState
import ru.tesseract.settings.data.Settings
import ru.tesseract.ui.theme.ThemeSetting

@Factory
class SettingsViewModel(
    private val loginState: LoginState,
    private val settings: Settings,
) : ViewModel() {
    private val allowChangingPasswordFlow = loginState.loginMethod
        .map { it?.allowChangingPassword ?: false }

    fun onLogOut() = viewModelScope.launch {
        loginState.resetToken()
    }

    fun onSetTheme(value: ThemeSetting) = viewModelScope.launch {
        settings.setTheme(value)
    }

    val state: State
        @Composable get() {
            val themeSetting by settings.themeSetting.collectAsState()
            val allowChangingPassword by allowChangingPasswordFlow.collectAsState(initial = false)
            return State(
                themeSetting = themeSetting,
                allowChangingPassword = allowChangingPassword,
            )
        }

    class State(
        val themeSetting: ThemeSetting,
        val allowChangingPassword: Boolean,
    )
}
