package ru.tesseract.diversifications.ui

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
import ru.tesseract.diversifications.api.DiversificationsApi
import ru.tesseract.diversifications.domain.DiversificationWithAssets

class DiversificationViewModelTest {
    @Test
    fun `DiversificationViewModel should load diversification from api`() = runTest {
        val expectedDiversification = DiversificationWithAssets(
            at = Clock.System.now(),
            riskLevelOrdinal = 0,
            currentAmount = 100,
            amountDiff = 100,
            assets = emptyList(),
        )
        val api = mockk<DiversificationsApi>()
        coEvery { api.get(0) } returns ApiResponse.Success(expectedDiversification)
        val viewModel = DiversificationViewModel(0, api)

        moleculeFlow(RecompositionMode.Immediate) {
            viewModel.diversification()
        }.test {
            assertEquals(null, awaitItem())
            assertEquals(expectedDiversification, awaitItem())
        }
    }
}
