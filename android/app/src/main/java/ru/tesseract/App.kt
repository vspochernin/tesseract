package ru.tesseract

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import ru.tesseract.ui.theme.TesseractTheme

@Composable
fun App() {
    TesseractTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ErrorHandling()
            Root()
        }
    }
}
