package ru.tesseract.assets

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick

@OptIn(ExperimentalTestApi::class)
class AssetRobot(private val composeTestRule: ComposeTestRule) {
    fun waitUntilLoaded() = with(composeTestRule) {
        waitUntilAtLeastOneExists(hasTestTag("AssetsScreen.Asset"))
    }

    fun goToAnyAsset() = with(composeTestRule) {
        waitUntilLoaded()
        onAllNodesWithTag("AssetsScreen.Asset").onFirst().performClick()
    }

    fun addAnyAssetToFavorites() = with(composeTestRule) {
        waitUntilLoaded()
        onAllNodesWithTag("AssetSummary.FavoriteButton").onFirst().performClick()
    }

    fun assertDescriptionIsShown() = with(composeTestRule) {
        waitUntilAtLeastOneExists(hasTestTag("AssetScreen.AssetDescription"))
    }
}
