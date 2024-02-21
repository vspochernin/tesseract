package ru.tesseract

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class RegistrationScreenTest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Test
    fun givenValidDetails_whenRegister_thenRegistrationIsSuccessful() {
        tryToRegister(
            login = "vspoch",
            email = "email@gmail.com",
            password = "password0",
            confirmPassword = "password0",
        )
        composeTestRule.waitUntilDoesNotExist(hasTestTag("confirm_register_button"))
    }

    @Test
    fun givenDuplicateLogin_whenRegister_thenErrorDialogIsShown() {
        tryToRegister(
            login = "vspochernin",
            email = "vspochernin@gmail.com",
            password = "password0",
            confirmPassword = "password0",
        )
        composeTestRule.waitUntilDoesNotExist(hasTestTag("error_dialog"))
    }

    private fun tryToRegister(
        login: String,
        email: String,
        password: String,
        confirmPassword: String,
    ) {
        launchActivity<MainActivity>()
        with(composeTestRule) {
            onNodeWithTag("register_button").performClick()
            onNodeWithTag("login_field").performTextInput(login)
            onNodeWithTag("email_field").performTextInput(email)
            onNodeWithTag("password_field").performTextInput(password)
            onNodeWithTag("confirm_password_field").performTextInput(confirmPassword)
            onNodeWithTag("confirm_register_button").performClick()
        }
    }
}
