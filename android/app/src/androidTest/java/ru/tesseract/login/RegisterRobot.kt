package ru.tesseract.login

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import ru.tesseract.waitUntilAtLeastOneExistsWithTag
import ru.tesseract.waitUntilDoesNotExistWithTag

class RegisterRobot(private val composeTestRule: ComposeTestRule) {
    fun navigate() = with(composeTestRule) {
        onNodeWithTag("LoginScreen.RegisterButton").performClick()
        waitUntilAtLeastOneExistsWithTag("RegistrationScreen.LoginField")
    }

    fun inputLogin(text: String) = with(composeTestRule) {
        onNodeWithTag("RegistrationScreen.LoginField").performTextInput(text)
    }

    fun inputEmail(text: String) = with(composeTestRule) {
        onNodeWithTag("RegistrationScreen.EmailField").performTextInput(text)
    }

    fun inputPassword(text: String) = with(composeTestRule) {
        onNodeWithTag("RegistrationScreen.PasswordField").performTextInput(text)
    }

    fun inputConfirmPassword(text: String) = with(composeTestRule) {
        onNodeWithTag("RegistrationScreen.ConfirmPasswordField").performTextInput(text)
    }

    fun confirm() = with(composeTestRule) {
        onNodeWithTag("RegistrationScreen.ConfirmButton").performClick()
    }

    fun assertRegistered() = with(composeTestRule) {
        waitUntilDoesNotExistWithTag("RegistrationScreen.ConfirmButton")
    }

    fun tryToRegister(
        login: String,
        email: String,
        password: String,
        confirmPassword: String = password,
    ) {
        navigate()
        inputLogin(login)
        inputEmail(email)
        inputPassword(password)
        inputConfirmPassword(confirmPassword)
        confirm()
    }
}
