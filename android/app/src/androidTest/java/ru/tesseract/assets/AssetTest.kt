package ru.tesseract.assets

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.tesseract.startLoggedIn

class AssetTest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()
    private val assetRobot = AssetRobot(composeTestRule)

    @Before
    fun start() = startLoggedIn()

    @Test
    fun whenGoToAsset_thenAssetIsLoadedSuccessfully() = with(assetRobot) {
        goToAnyAsset()
        assertDescriptionIsShown()
    }

    @Test
    fun whenSwipeUp_thenAllAssetsAreShown() = with(assetRobot) {
        val seenAssets = mutableSetOf<String>()
        val expectedSize = 20
        repeat(15) {
            seenAssets.addAll(getVisibleAssetTitles())
            if (seenAssets.size >= expectedSize) return@repeat
            scroll()
        }
        assertEquals(expectedSize, seenAssets.size)
    }
}
