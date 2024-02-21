package ru.tesseract

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.GlobalContext

@OptIn(ExperimentalTestApi::class)
class LoginTest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Before
    fun initialize() = runTest {
        val loginState = GlobalContext.get().get<LoginState>()
        loginState.resetToken()
    }

    @Test
    fun givenValidDetails_whenLogin_thenLoginIsSuccessful() {
        tryToLogin(login = "vspochernin", password = "qwe123")
        composeTestRule.waitUntilDoesNotExist(hasTestTag("LoginScreen.ConfirmButton"))
    }

    @Test
    fun givenInvalidPassword_whenLogin_thenLoginIsUnsuccessful() {
        tryToLogin(login = "vspochernin", password = "invalid")
        composeTestRule.waitUntilAtLeastOneExists(hasTestTag("ErrorHandling.Dialog"))
    }

    private fun tryToLogin(
        login: String,
        password: String,
    ) {
        launchActivity<MainActivity>()
        with(composeTestRule) {
            onNodeWithTag("LoginScreen.LoginField").performTextInput(login)
            onNodeWithTag("LoginScreen.PasswordField").performTextInput(password)
            onNodeWithTag("LoginScreen.ConfirmButton").performClick()
        }
    }
}
