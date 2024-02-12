package ru.tesseract.assets.ui

import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test
import ru.tesseract.assets.api.AssetsApi

class AssetsViewModelTest {
    @Test
    fun `AssetsViewModel should initialize correctly`() {
        val api = mockk<AssetsApi>()
        AssetsViewModel(api)
    }
}
