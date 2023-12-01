package ru.tesseract.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import org.koin.core.annotation.Factory
import ru.tesseract.settings.data.Settings
import ru.tesseract.ui.theme.ThemeSetting

@Factory
class ThemeViewModel(
    private val setting: Settings
) : ViewModel() {
    val themeSetting: ThemeSetting
        @Composable get() {
            val value by setting.themeSetting.collectAsState()
            return value
        }
}
