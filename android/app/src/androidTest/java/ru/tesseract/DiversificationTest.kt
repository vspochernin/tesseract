package ru.tesseract

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class DiversificationTest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Before
    fun start() = startLoggedIn()

    @Test
    fun whenCreateDiversification_thenDiversificationIsCreated() {
        with(composeTestRule) {
            onNodeWithTag("Navigation.Diversifications").performClick()
            onNodeWithTag("DiversificationsScreen.CreateButton").performClick()
            onNodeWithTag("NewDiversificationScreen.AmountField").performTextInput("10000")
            onNodeWithTag("NewDiversificationScreen.CreateButton").performClick()
            waitUntilAtLeastOneExists(hasTestTag("DiversificationsScreen.Diversification"), timeoutMillis = 5_000)
            onAllNodesWithTag("DiversificationsScreen.Diversification").assertCountEquals(2)
        }
    }
}
