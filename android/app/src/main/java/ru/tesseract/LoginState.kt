package ru.tesseract

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.koin.core.annotation.Single

@Single
class LoginState {
    var isLoggedIn by mutableStateOf(true)
}
