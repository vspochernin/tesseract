package ru.tesseract.portfolios.ui

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.tesseract.api.ApiResponse
import ru.tesseract.portfolios.api.PortfoliosApi
import ru.tesseract.portfolios.domain.PortfolioWithAssets

class PortfolioViewModelTest {
    @Test
    fun `PortfolioViewModel should load portfolio from api`() = runTest {
        val expectedPortfolio = PortfolioWithAssets(
            at = Clock.System.now(),
            riskLevelOrdinal = 0,
            currentAmount = 100,
            amountDiff = 100,
            assets = emptyList(),
        )
        val api = mockk<PortfoliosApi>()
        coEvery { api.get(0) } returns ApiResponse.Success(expectedPortfolio)
        val viewModel = PortfolioViewModel(0, api)

        moleculeFlow(RecompositionMode.Immediate) {
            viewModel.portfolio()
        }.test {
            assertEquals(null, awaitItem())
            assertEquals(expectedPortfolio, awaitItem())
        }
    }
}
