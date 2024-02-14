package ru.tesseract.assets.ui

import io.mockk.mockk
import org.junit.Test
import ru.tesseract.assets.api.AssetsApi

class FavoritesViewModelTest {
    @Test
    fun `FavoritesViewModel should initialize correctly`() {
        val api = mockk<AssetsApi>()
        FavoritesViewModel(api)
    }
}
