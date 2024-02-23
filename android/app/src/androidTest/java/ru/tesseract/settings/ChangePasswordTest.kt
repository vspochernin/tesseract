package ru.tesseract.settings

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.tesseract.errorhandling.ErrorRobot
import ru.tesseract.login.LoginRobot
import ru.tesseract.startLoggedOut

class ChangePasswordTest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()
    private val loginRobot = LoginRobot(composeTestRule)
    private val settingsRobot = SettingsRobot(composeTestRule)
    private val errorRobot = ErrorRobot(composeTestRule)

    @Before
    fun start() = startLoggedOut()

    @Test
    fun givenValidDetails_whenChangePassword_thenPasswordIsChanged() {
        val login = "vrazukrantov"
        val old = "qwe123"
        val new = "password0"

        loginRobot.tryToLogin(login, old)
        loginRobot.assertLoggedIn()

        settingsRobot.tryToChangePassword(old, new)
        settingsRobot.assertSuccessDialogIsShown()
        settingsRobot.dismissSuccessDialog()
        settingsRobot.logOut()

        loginRobot.tryToLogin(login, old)
        errorRobot.assertErrorDialogIsShown()
        errorRobot.dismiss()

        loginRobot.inputPassword(new)
        loginRobot.confirm()
        loginRobot.assertLoggedIn()
    }
}
