package ru.tesseract.assets

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.tesseract.startLoggedIn

class FavoriteTest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()
    private val assetRobot = AssetRobot(composeTestRule)
    private val favoritesRobot = FavoritesRobot(composeTestRule)

    @Before
    fun start() = startLoggedIn()

    @Test
    fun whenAddFavorite_thenAssetIsAddedToFavorites() {
        runBlocking {
            assetRobot.addAnyAssetToFavorites()
            delay(500)
            favoritesRobot.navigateToTab()
            favoritesRobot.assertFavoritesCount(1)
        }
    }
}
