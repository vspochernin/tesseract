package ru.tesseract

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.compose.getKoin
import org.koin.core.Koin
import org.koin.core.context.GlobalContext

@OptIn(ExperimentalTestApi::class)
class RegistrationTest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Before
    fun start() = startNotLoggedIn()

    @Test
    fun givenValidDetails_whenRegister_thenRegistrationIsSuccessful() {
        tryToRegister(
            login = "vspoch",
            email = "email@gmail.com",
            password = "password0",
            confirmPassword = "password0",
        )
        composeTestRule.waitUntilDoesNotExist(hasTestTag("RegistrationScreen.ConfirmButton"))
    }

    @Test
    fun givenDuplicateLogin_whenRegister_thenErrorDialogIsShown() {
        tryToRegister(
            login = "vspochernin",
            email = "vspochernin@gmail.com",
            password = "password0",
            confirmPassword = "password0",
        )
        composeTestRule.waitUntilAtLeastOneExists(hasTestTag("ErrorHandling.Dialog"))
    }

    private fun tryToRegister(
        login: String,
        email: String,
        password: String,
        confirmPassword: String,
    ) {
        launchActivity<MainActivity>()
        with(composeTestRule) {
            onNodeWithTag("LoginScreen.RegisterButton").performClick()
            onNodeWithTag("RegistrationScreen.LoginField").performTextInput(login)
            onNodeWithTag("RegistrationScreen.EmailField").performTextInput(email)
            onNodeWithTag("RegistrationScreen.PasswordField").performTextInput(password)
            onNodeWithTag("RegistrationScreen.ConfirmPasswordField").performTextInput(confirmPassword)
            onNodeWithTag("RegistrationScreen.ConfirmButton").performClick()
        }
    }
}
