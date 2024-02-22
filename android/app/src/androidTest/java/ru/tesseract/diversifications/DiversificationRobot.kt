package ru.tesseract.diversifications

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput

@OptIn(ExperimentalTestApi::class)
class DiversificationRobot(private val composeTestRule: ComposeTestRule) {
    fun navigateToTab() = with(composeTestRule) {
        onNodeWithTag("Navigation.Diversifications").performClick()
    }

    fun navigateToCreate() = with(composeTestRule) {
        onNodeWithTag("DiversificationsScreen.CreateButton").performClick()
    }

    fun inputAmount(text: String) = with(composeTestRule) {
        onNodeWithTag("NewDiversificationScreen.AmountField").performTextInput(text)
    }

    fun create() = with(composeTestRule) {
        onNodeWithTag("NewDiversificationScreen.CreateButton").performClick()
    }

    fun assertDiversificationCount(count: Int) = with(composeTestRule) {
        waitUntilAtLeastOneExists(hasTestTag("DiversificationsScreen.Diversification"))
        onAllNodesWithTag("DiversificationsScreen.Diversification").assertCountEquals(2)
    }

    fun tryToCreateDiversification(amount: String) {
        navigateToTab()
        navigateToCreate()
        inputAmount(amount)
        create()
    }
}
