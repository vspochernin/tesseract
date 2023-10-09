package ru.tesseract

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel
import ru.tesseract.ui.navigation.LoginNavigation
import ru.tesseract.ui.navigation.MainNavigation
import ru.tesseract.ui.theme.TesseractTheme

@Composable
fun App(viewModel: RootViewModel = koinViewModel()) {
    TesseractTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AnimatedContent(targetState = viewModel.isLoggedIn) { isLoggedIn ->
                if (isLoggedIn) {
                    MainNavigation()
                } else {
                    LoginNavigation()
                }
            }
        }
    }
}
