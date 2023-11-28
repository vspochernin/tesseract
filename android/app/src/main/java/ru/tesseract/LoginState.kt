package ru.tesseract

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.koin.core.annotation.Single

@Single
class LoginState {
    var token by mutableStateOf<String?>(null)
    val isLoggedIn by derivedStateOf { token != null }
}
