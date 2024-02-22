package ru.tesseract

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class AssetTest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Before
    fun start() = startLoggedIn()

    @Test
    fun whenGoToAsset_thenAssetIsLoadedSuccessfully() {
        with(composeTestRule) {
            waitUntilAtLeastOneExists(hasTestTag("AssetsScreen.Asset"), timeoutMillis = 5_000)
            onAllNodesWithTag("AssetsScreen.Asset").onFirst().performClick()
            waitUntilAtLeastOneExists(hasTestTag("AssetScreen.AssetDescription"), timeoutMillis = 5_000)
        }
    }
}
