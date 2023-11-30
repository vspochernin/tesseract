package ru.tesseract.ui.theme

import androidx.annotation.StringRes
import ru.tesseract.R

enum class ThemeSetting(@StringRes val displayName: Int) {
    System(R.string.settings_screen_theme_system),
    Light(R.string.settings_screen_theme_light),
    Dark(R.string.settings_screen_theme_dark),
}
