package ru.tesseract.settings

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.tesseract.errorhandling.ErrorRobot
import ru.tesseract.login.LoginRobot
import ru.tesseract.startLoggedOut

private const val LOGIN = "vrazukrantov"
private const val OLD_PASSWORD = "qwe123"
private const val NEW_PASSWORD = "password0"

class ChangePasswordTest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()
    private val loginRobot = LoginRobot(composeTestRule)
    private val settingsRobot = SettingsRobot(composeTestRule)
    private val errorRobot = ErrorRobot(composeTestRule)

    @Before
    fun start() {
        startLoggedOut()
        loginRobot.tryToLogin(LOGIN, OLD_PASSWORD)
        loginRobot.assertLoggedIn()
    }

    @Test
    fun givenValidDetails_whenChangePassword_thenPasswordIsChanged() {
        settingsRobot.tryToChangePassword(OLD_PASSWORD, NEW_PASSWORD)
        settingsRobot.assertSuccessDialogIsShown()
        settingsRobot.dismissSuccessDialog()
        settingsRobot.logOut()

        loginRobot.tryToLogin(LOGIN, OLD_PASSWORD)
        errorRobot.assertErrorDialogIsShown()
        errorRobot.dismiss()

        loginRobot.inputPassword(NEW_PASSWORD)
        loginRobot.confirm()
        loginRobot.assertLoggedIn()
    }

    @Test
    fun givenInvalidOldPassword_whenChangePassword_thenErrorDialogIsShown() {
        settingsRobot.tryToChangePassword("invalid", NEW_PASSWORD)
        errorRobot.assertErrorDialogIsShown()
    }
}
