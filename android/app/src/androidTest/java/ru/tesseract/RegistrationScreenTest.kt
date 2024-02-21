package ru.tesseract

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class RegistrationScreenTest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Test
    fun givenValidDetails_whenRegister_thenRegistrationIsSuccessful() {
        launchActivity<MainActivity>()
        navigateToRegisterScreen()
        inputLogin("vspoch")
        inputEmail("mail@gmail.com")
        inputPassword("password0")
        inputConfirmPassword("password0")
        finishRegistration()
        assertSuccess()
    }

    private fun navigateToRegisterScreen() {
        composeTestRule.onNodeWithTag("register_button").performClick()
    }

    private fun inputLogin(text: String) {
        composeTestRule.onNodeWithTag("login_field").performTextInput(text)
    }

    private fun inputEmail(text: String) {
        composeTestRule.onNodeWithTag("email_field").performTextInput(text)
    }

    private fun inputPassword(text: String) {
        composeTestRule.onNodeWithTag("password_field").performTextInput(text)
    }

    private fun inputConfirmPassword(text: String) {
        composeTestRule.onNodeWithTag("confirm_password_field").performTextInput(text)
    }

    private fun finishRegistration() {
        composeTestRule.onNodeWithTag("confirm_register_button").performClick()
    }

    @OptIn(ExperimentalTestApi::class)
    private fun assertSuccess() {
        composeTestRule.waitUntilDoesNotExist(hasTestTag("confirm_register_button"))
    }
}
