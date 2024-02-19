package ru.tesseract.ui

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import ru.tesseract.settings.data.Settings
import ru.tesseract.ui.theme.ThemeSetting

class ThemeViewModelTest {
    @Test
    fun `ThemeViewModel should return correct theme setting`() = runTest {
        val settings = mockk<Settings>()
        val themeSettingFlow = MutableStateFlow(ThemeSetting.Dark)
        every { settings.themeSetting } returns themeSettingFlow
        val viewModel = ThemeViewModel(settings)
        moleculeFlow(RecompositionMode.Immediate) {
            viewModel.themeSetting
        }.test {
            assertEquals(ThemeSetting.Dark, awaitItem())
        }
    }
}
