package ru.tesseract.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import org.koin.core.annotation.Factory
import ru.tesseract.LoginState

@Factory
class RootViewModel(
    private val loginState: LoginState,
) : ViewModel() {
    val isLoggedIn: Boolean
        @Composable get() {
            val token by loginState.token.collectAsState()
            return token != null
        }
}
