package ru.tesseract.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel
import ru.tesseract.ui.navigation.LoginNavigation
import ru.tesseract.ui.navigation.MainNavigation

@Composable
fun Root(viewModel: RootViewModel = koinViewModel()) {
    AnimatedContent(targetState = viewModel.isLoggedIn, label = "LoginState") { isLoggedIn ->
        if (isLoggedIn) {
            MainNavigation()
        } else {
            LoginNavigation()
        }
    }
}
