package ru.tesseract.login

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import ru.tesseract.waitUntilAtLeastOneExistsWithTag
import ru.tesseract.waitUntilDoesNotExistWithTag

class LoginRobot(private val composeTestRule: ComposeTestRule) {
    fun inputLogin(text: String) = with(composeTestRule) {
        onNodeWithTag("LoginScreen.LoginField").performTextInput(text)
    }

    fun inputPassword(text: String) = with(composeTestRule) {
        onNodeWithTag("LoginScreen.PasswordField").performTextInput(text)
    }

    fun confirm() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("LoginScreen.ConfirmButton")
        onNodeWithTag("LoginScreen.ConfirmButton").performClick()
    }

    fun assertLoggedIn() = with(composeTestRule) {
        waitUntilDoesNotExistWithTag("LoginScreen.ConfirmButton")
    }

    fun waitUntilLoaded() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("LoginScreen.LoginField")
    }

    fun tryToLogin(login: String, password: String) {
        waitUntilLoaded()
        inputLogin(login)
        inputPassword(password)
        confirm()
    }
}
