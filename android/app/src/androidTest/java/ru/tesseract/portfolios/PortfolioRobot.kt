package ru.tesseract.portfolios

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import ru.tesseract.waitUntilAtLeastOneExistsWithTag

class PortfolioRobot(private val composeTestRule: ComposeTestRule) {
    fun navigateToTab() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("Navigation.Portfolios")
        onNodeWithTag("Navigation.Portfolios").performClick()
    }

    fun navigateToCreate() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("PortfoliosScreen.CreateButton")
        onNodeWithTag("PortfoliosScreen.CreateButton").performClick()
    }

    fun inputAmount(text: String) = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("NewPortfolioScreen.AmountField")
        onNodeWithTag("NewPortfolioScreen.AmountField").performTextInput(text)
    }

    fun create() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("NewPortfolioScreen.CreateButton")
        onNodeWithTag("NewPortfolioScreen.CreateButton").performClick()
    }

    fun waitUntilLoaded() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("PortfoliosScreen.Portfolio")
    }

    fun assertPortfolioCount(count: Int) = with(composeTestRule) {
        waitUntilLoaded()
        onAllNodesWithTag("PortfoliosScreen.Portfolio").assertCountEquals(count)
    }

    fun goToAnyPortfolio() = with(composeTestRule) {
        waitUntilLoaded()
        onAllNodesWithTag("PortfoliosScreen.Portfolio").onFirst().performClick()
    }

    fun assertPortfolioAssetsAreShown() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("PortfolioScreen.Asset")
    }

    fun tryToCreatePortfolio(amount: String) {
        navigateToTab()
        navigateToCreate()
        inputAmount(amount)
        create()
    }
}
