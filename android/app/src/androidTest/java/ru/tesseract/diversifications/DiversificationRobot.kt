package ru.tesseract.diversifications

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import ru.tesseract.waitUntilAtLeastOneExistsWithTag

class DiversificationRobot(private val composeTestRule: ComposeTestRule) {
    fun navigateToTab() = with(composeTestRule) {
        onNodeWithTag("Navigation.Diversifications").performClick()
    }

    fun navigateToCreate() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("DiversificationsScreen.CreateButton")
        onNodeWithTag("DiversificationsScreen.CreateButton").performClick()
    }

    fun inputAmount(text: String) = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("NewDiversificationScreen.AmountField")
        onNodeWithTag("NewDiversificationScreen.AmountField").performTextInput(text)
    }

    fun create() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("NewDiversificationScreen.CreateButton")
        onNodeWithTag("NewDiversificationScreen.CreateButton").performClick()
    }

    fun waitUntilLoaded() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("DiversificationsScreen.Diversification")
    }

    fun assertDiversificationCount(count: Int) = with(composeTestRule) {
        waitUntilLoaded()
        onAllNodesWithTag("DiversificationsScreen.Diversification").assertCountEquals(count)
    }

    fun goToAnyDiversification() = with(composeTestRule) {
        waitUntilLoaded()
        onAllNodesWithTag("DiversificationsScreen.Diversification").onFirst().performClick()
    }

    fun assertDiversificationAssetsAreShown() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("DiversificationScreen.Asset")
    }

    fun tryToCreateDiversification(amount: String) {
        navigateToTab()
        navigateToCreate()
        inputAmount(amount)
        create()
    }
}
