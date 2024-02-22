package ru.tesseract

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
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
    fun givenValidAmount_whenCreateDiversification_thenDiversificationIsCreated() {
        tryToCreateDiversification("10000")
        with(composeTestRule) {
            waitUntilAtLeastOneExists(hasTestTag("DiversificationsScreen.Diversification"))
            onAllNodesWithTag("DiversificationsScreen.Diversification").assertCountEquals(2)
        }
    }

    @Test
    fun givenAmountSmallerThanMinPrice_whenCreateDiversification_thenErrorDialogIsShown() {
        tryToCreateDiversification("1")
        with(composeTestRule) {
            waitUntilAtLeastOneExists(hasTestTag("ErrorHandling.Dialog"))
        }
    }

    private fun tryToCreateDiversification(amount: String) {
        with(composeTestRule) {
            onNodeWithTag("Navigation.Diversifications").performClick()
            onNodeWithTag("DiversificationsScreen.CreateButton").performClick()
            onNodeWithTag("NewDiversificationScreen.AmountField").performTextInput(amount)
            onNodeWithTag("NewDiversificationScreen.CreateButton").performClick()
        }
    }
}
