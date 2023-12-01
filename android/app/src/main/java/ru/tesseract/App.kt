package ru.tesseract

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel
import ru.tesseract.ui.ErrorHandling
import ru.tesseract.ui.Root
import ru.tesseract.ui.ThemeViewModel
import ru.tesseract.ui.theme.TesseractTheme

@Composable
fun App(viewModel: ThemeViewModel = koinViewModel()) {
    TesseractTheme(viewModel.themeSetting) {
        Surface(color = MaterialTheme.colorScheme.background) {
            ErrorHandling()
            Root()
        }
    }
}
