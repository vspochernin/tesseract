package ru.tesseract

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class FavoriteTest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Before
    fun start() = startLoggedIn()

    @Test
    fun whenAddFavorite_thenAssetIsAddedToFavorites() {
        with(composeTestRule) {
            waitUntilAtLeastOneExists(hasTestTag("AssetsScreen.Asset"))
            onAllNodesWithTag("AssetSummary.FavoriteButton").onFirst().performClick()
            onNodeWithTag("Navigation.Favorites").performClick()
            waitUntilAtLeastOneExists(hasTestTag("FavoritesScreen.Asset"))
            onAllNodesWithTag("FavoritesScreen.Asset").assertCountEquals(1)
        }
    }
}
