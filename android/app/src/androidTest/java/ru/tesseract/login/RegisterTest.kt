package ru.tesseract.login

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.tesseract.errorhandling.ErrorRobot
import ru.tesseract.startLoggedOut

class RegisterTest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()
    private val registerRobot = RegisterRobot(composeTestRule)
    private val loginRobot = LoginRobot(composeTestRule)

    @Before
    fun start() = startLoggedOut()

    @Test
    fun givenValidDetails_whenRegister_thenRegistrationIsSuccessful() {
        val login = "vspoch"
        val password = "password0"
        registerRobot.tryToRegister(
            login = login,
            email = "email@gmail.com",
            password = password,
        )
        registerRobot.assertRegistered()
        loginRobot.tryToLogin(login, password)
        loginRobot.assertLoggedIn()
    }

    @Test
    fun givenDuplicateLogin_whenRegister_thenErrorDialogIsShown() {
        registerRobot.tryToRegister(
            login = "vspochernin",
            email = "vspochernin@gmail.com",
            password = "password0",
        )
        ErrorRobot(composeTestRule).assertErrorDialogIsShown()
    }
}
