package ru.tesseract.assets

import androidx.compose.ui.test.junit4.createEmptyComposeRule
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
}
