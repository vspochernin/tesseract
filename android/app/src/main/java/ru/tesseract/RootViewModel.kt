package ru.tesseract

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import org.koin.core.annotation.Factory
import ru.tesseract.api.ApiClient

@Factory
class RootViewModel(
    private val loginState: LoginState,
) : ViewModel() {
    val isLoggedIn: Boolean
        @Composable get() = loginState.isLoggedIn
}
