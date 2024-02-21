package ru.tesseract

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import kotlinx.coroutines.test.runTest
import org.koin.core.context.GlobalContext

fun startNotLoggedIn() = runTest {
    launchActivity<MainActivity>()
    val loginState = GlobalContext.get().get<LoginState>()
    loginState.resetToken()
}

@OptIn(ExperimentalTestApi::class)
fun startLoggedIn(composeTestRule: ComposeTestRule) = runTest {
    launchActivity<MainActivity>()
    val loginState = GlobalContext.get().get<LoginState>()
    if (loginState.token.value != null) return@runTest
    with(composeTestRule) {
        onNodeWithTag("LoginScreen.LoginField").performTextInput("vspochernin")
        onNodeWithTag("LoginScreen.PasswordField").performTextInput("qwe123")
        onNodeWithTag("LoginScreen.ConfirmButton").performClick()
        waitUntilDoesNotExist(hasTestTag("LoginScreen.ConfirmButton"))
    }
}
