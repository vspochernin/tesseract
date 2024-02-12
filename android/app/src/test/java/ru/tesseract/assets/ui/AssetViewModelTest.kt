package ru.tesseract.assets.ui

import io.mockk.mockk
import org.junit.Test
import ru.tesseract.assets.api.AssetsApi

class AssetViewModelTest {
    @Test
    fun `AssetViewModel should initialize correctly`() {
        val api = mockk<AssetsApi>()
        AssetViewModel(0, api)
    }
}
