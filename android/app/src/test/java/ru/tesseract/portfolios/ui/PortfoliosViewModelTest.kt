package ru.tesseract.portfolios.ui

import androidx.paging.testing.asSnapshot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.tesseract.api.ApiResponse
import ru.tesseract.portfolios.api.PortfoliosApi

class PortfoliosViewModelTest {
    @Test
    fun `PortfoliosViewModel should paginate api requests`() = runTest {
        val api = mockk<PortfoliosApi>()
        coEvery { api.getAll(any()) } returns ApiResponse.Success(emptyList())
        val viewModel = PortfoliosViewModel(api)
        viewModel.portfolios.asSnapshot {
            scrollTo(1)
        }
        coVerify(exactly = 1) { api.getAll(0) }
        coVerify(exactly = 0) { api.getAll(1) }
    }
}
