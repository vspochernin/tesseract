package ru.tesseract.assets

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import ru.tesseract.waitUntilAtLeastOneExistsWithTag

class FavoritesRobot(private val composeTestRule: ComposeTestRule) {
    fun navigateToTab() = with(composeTestRule) {
        onNodeWithTag("Navigation.Favorites").performClick()
    }

    fun assertFavoritesCount(count: Int) = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("FavoritesScreen.Asset")
        onAllNodesWithTag("FavoritesScreen.Asset").assertCountEquals(1)
    }
}
