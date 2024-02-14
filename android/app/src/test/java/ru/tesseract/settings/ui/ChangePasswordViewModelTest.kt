package ru.tesseract.settings.ui

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import ru.tesseract.api.ApiErrorType
import ru.tesseract.api.ApiResponse
import ru.tesseract.settings.api.PasswordApi
import ru.tesseract.util.MainDispatcherRule

class ChangePasswordViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `ChangePasswordViewModel should not enable button when fields are empty`() {
        val api = mockk<PasswordApi>()
        val viewModel = ChangePasswordViewModel(api)
        assertFalse(viewModel.isButtonEnabled)
        viewModel.oldPassword = "p"
        assertFalse(viewModel.isButtonEnabled)
        viewModel.password = "p"
        assertFalse(viewModel.isButtonEnabled)
        viewModel.confirmPassword = "p"
        assertTrue(viewModel.isButtonEnabled)
    }

    @Test
    fun `ChangePasswordViewModel should call api`() = runTest {
        val api = mockk<PasswordApi>()
        coEvery { api.change(any(), any()) } returns ApiResponse.Success(Unit)
        val viewModel = ChangePasswordViewModel(api)
        viewModel.password = "password123"
        viewModel.confirmPassword = "password123"
        viewModel.onChange().join()
        coVerify(exactly = 1) { api.change(any(), "password123") }
        confirmVerified(api)
    }

    @Test
    fun `ChangePasswordViewModel should clear old password on error`() = runTest {
        val api = mockk<PasswordApi>()
        coEvery { api.change(any(), any()) } returns ApiResponse.ApiError(ApiErrorType.PasswordDoesNotMatch)
        val viewModel = ChangePasswordViewModel(api)
        viewModel.oldPassword = "password"
        viewModel.password = "password123"
        viewModel.confirmPassword = "password123"
        viewModel.onChange().join()
        assertEquals("", viewModel.oldPassword)
        coVerify(exactly = 1) { api.change(any(), "password123") }
        confirmVerified(api)
    }

    @Test
    fun `ChangePasswordViewModel should clear new password on error`() = runTest {
        val api = mockk<PasswordApi>()
        coEvery { api.change(any(), any()) } returns ApiResponse.ApiError(ApiErrorType.IncorrectPassword)
        val viewModel = ChangePasswordViewModel(api)
        viewModel.password = "password123"
        viewModel.confirmPassword = "password123"
        viewModel.onChange().join()
        assertEquals("", viewModel.password)
        assertEquals("", viewModel.confirmPassword)
        coVerify(exactly = 1) { api.change(any(), "password123") }
        confirmVerified(api)
    }
}
