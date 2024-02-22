package ru.tesseract.assets

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick

@OptIn(ExperimentalTestApi::class)
class FavoritesRobot(private val composeTestRule: ComposeTestRule) {
    fun navigateToTab() = with(composeTestRule) {
        onNodeWithTag("Navigation.Favorites").performClick()
    }

    fun assertFavoritesCount(count: Int) = with(composeTestRule) {
        waitUntilAtLeastOneExists(hasTestTag("FavoritesScreen.Asset"))
        onAllNodesWithTag("FavoritesScreen.Asset").assertCountEquals(1)
    }
}
