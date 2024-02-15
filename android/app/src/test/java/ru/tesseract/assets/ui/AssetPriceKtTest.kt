package ru.tesseract.assets.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class AssetPriceKtTest {
    @Test
    fun `formatPrice should format price correctly`() {
        assertEquals("123,00 ₽", formatPrice(12300L))
    }
}
