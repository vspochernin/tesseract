package ru.tesseract.assets

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import ru.tesseract.waitUntilAtLeastOneExistsWithTag

class AssetRobot(private val composeTestRule: ComposeTestRule) {
    fun waitUntilLoaded() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("AssetsScreen.Asset")
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
        waitUntilAtLeastOneExistsWithTag("AssetScreen.AssetDescription")
    }

    fun scrollTo(index: Int) = with(composeTestRule) {
        runCatching {
            onNodeWithTag("AssetsScreen.List").performScrollToIndex(index)
        }
    }

    fun getVisibleAssetTitles(): List<String> = with(composeTestRule) {
        onAllNodesWithTag("AssetSummary.Title", useUnmergedTree = true)
            .fetchSemanticsNodes()
            .map { it.config[SemanticsProperties.Text].toString() }
    }
}
