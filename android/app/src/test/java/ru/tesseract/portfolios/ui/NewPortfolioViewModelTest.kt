package ru.tesseract.diversifications.ui

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import ru.tesseract.api.ApiResponse
import ru.tesseract.diversifications.api.DiversificationsApi
import ru.tesseract.diversifications.domain.RiskLevel
import ru.tesseract.util.MainDispatcherRule

class NewDiversificationViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `NewDiversificationViewModel should call create diversification`() = runTest {
        val api = mockk<DiversificationsApi>()
        coEvery { api.create(any(), any()) } returns ApiResponse.Success(Unit)
        val dismiss = mockk<() -> Unit>()
        every { dismiss() } returns Unit
        val viewModel = NewDiversificationViewModel(api)
        viewModel.amountField = "123"
        viewModel.onCreate(dismiss).join()
        coVerify(exactly = 1) { api.create(12300L, RiskLevel.Combined) }
        coVerify(exactly = 1) { dismiss() }
        confirmVerified(api)
        confirmVerified(dismiss)
    }

    @Test
    fun `NewDiversificationViewModel should not create invalid diversification`() = runTest {
        val api = mockk<DiversificationsApi>()
        coEvery { api.create(any(), any()) } returns ApiResponse.Success(Unit)
        val dismiss = mockk<() -> Unit>()
        every { dismiss() } returns Unit
        val viewModel = NewDiversificationViewModel(api)
        viewModel.amountField = "abc"
        viewModel.onCreate(dismiss).join()
        coVerify(exactly = 0) { api.create(any(), any()) }
        coVerify(exactly = 0) { dismiss() }
        confirmVerified(api)
        confirmVerified(dismiss)
    }
}
