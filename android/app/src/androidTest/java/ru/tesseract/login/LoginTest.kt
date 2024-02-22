package ru.tesseract.login

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.tesseract.errorhandling.ErrorRobot
import ru.tesseract.startLoggedOut

class LoginTest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()
    private val loginRobot = LoginRobot(composeTestRule)

    @Before
    fun start() = startLoggedOut()

    @Test
    fun givenValidDetails_whenLogin_thenLoginIsSuccessful() = with(loginRobot) {
        tryToLogin(login = "vspochernin", password = "qwe123")
        assertLoggedIn()
    }

    @Test
    fun givenInvalidPassword_whenLogin_thenLoginIsUnsuccessful() = with(loginRobot) {
        tryToLogin(login = "vspochernin", password = "invalid")
        ErrorRobot(composeTestRule).assertErrorDialogIsShown()
    }
}
