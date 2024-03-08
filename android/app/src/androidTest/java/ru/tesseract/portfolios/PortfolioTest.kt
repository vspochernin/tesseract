package ru.tesseract.portfolios

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.tesseract.errorhandling.ErrorRobot
import ru.tesseract.startLoggedIn

class PortfolioTest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()
    private val portfolioRobot = PortfolioRobot(composeTestRule)

    @Before
    fun start() = startLoggedIn()

    @Test
    fun whenGoToPortfolio_thenPortfolioAssetsAreShown() {
        portfolioRobot.navigateToTab()
        portfolioRobot.goToAnyPortfolio()
        portfolioRobot.assertPortfolioAssetsAreShown()
    }

    @Test
    fun givenValidAmount_whenCreatePortfolio_thenPortfolioIsCreated() {
        portfolioRobot.tryToCreatePortfolio("10000")
        portfolioRobot.assertPortfolioCount(2)
    }

    @Test
    fun givenAmountSmallerThanMinPrice_whenCreatePortfolio_thenErrorDialogIsShown() {
        portfolioRobot.tryToCreatePortfolio("1")
        ErrorRobot(composeTestRule).assertErrorDialogIsShown()
    }
}
