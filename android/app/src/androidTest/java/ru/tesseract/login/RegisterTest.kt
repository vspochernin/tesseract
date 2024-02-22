package ru.tesseract.login

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
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

    @Before
    fun start() = startLoggedOut()

    @Test
    fun givenValidDetails_whenRegister_thenRegistrationIsSuccessful() = with(registerRobot) {
        tryToRegister(
            login = "vspoch",
            email = "email@gmail.com",
            password = "password0",
            confirmPassword = "password0",
        )
        assertRegistered()
    }

    @Test
    fun givenDuplicateLogin_whenRegister_thenErrorDialogIsShown() = with(registerRobot) {
        tryToRegister(
            login = "vspochernin",
            email = "vspochernin@gmail.com",
            password = "password0",
            confirmPassword = "password0",
        )
        ErrorRobot(composeTestRule).assertErrorDialogIsShown()
    }
}
