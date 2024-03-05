package ru.tesseract.diversifications.ui

import androidx.paging.testing.asSnapshot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.tesseract.api.ApiResponse
import ru.tesseract.diversifications.api.DiversificationsApi

class DiversificationsViewModelTest {
    @Test
    fun `DiversificationsViewModel should paginate api requests`() = runTest {
        val api = mockk<DiversificationsApi>()
        coEvery { api.getAll(any()) } returns ApiResponse.Success(emptyList())
        val viewModel = DiversificationsViewModel(api)
        viewModel.diversifications.asSnapshot {
            scrollTo(1)
        }
        coVerify(exactly = 1) { api.getAll(0) }
        coVerify(exactly = 0) { api.getAll(1) }
    }
}
