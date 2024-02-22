package ru.tesseract.login

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import ru.tesseract.waitUntilDoesNotExistWithTag

class LoginRobot(private val composeTestRule: ComposeTestRule) {
    fun inputLogin(text: String) = with(composeTestRule) {
        onNodeWithTag("LoginScreen.LoginField").performTextInput(text)
    }

    fun inputPassword(text: String) = with(composeTestRule) {
        onNodeWithTag("LoginScreen.PasswordField").performTextInput(text)
    }

    fun confirm() = with(composeTestRule) {
        onNodeWithTag("LoginScreen.ConfirmButton").performClick()
    }

    fun assertLoggedIn() = with(composeTestRule) {
        waitUntilDoesNotExistWithTag("LoginScreen.ConfirmButton")
    }

    fun tryToLogin(login: String, password: String) {
        inputLogin(login)
        inputPassword(password)
        confirm()
    }
}
