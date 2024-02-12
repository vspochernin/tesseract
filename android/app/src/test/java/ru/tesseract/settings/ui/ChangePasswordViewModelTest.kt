package ru.tesseract.settings.ui

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Test
import ru.tesseract.api.ApiErrorType
import ru.tesseract.api.ApiResponse
import ru.tesseract.settings.api.PasswordApi

@OptIn(ExperimentalCoroutinesApi::class)
class ChangePasswordViewModelTest {
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
    fun `ChangePasswordViewModel should call api`() {
        val api = mockk<PasswordApi>()
        coEvery { api.change(any(), any()) } returns ApiResponse.Success(Unit)
        val viewModel = ChangePasswordViewModel(api)
        viewModel.password = "password123"
        viewModel.confirmPassword = "password123"
        Dispatchers.setMain(Dispatchers.Default)
        runTest {
            viewModel.onChange().join()
        }
        coVerify(exactly = 1) { api.change(any(), "password123") }
        confirmVerified(api)
    }

    @Test
    fun `ChangePasswordViewModel should clear old password on error`() {
        val api = mockk<PasswordApi>()
        coEvery { api.change(any(), any()) } returns ApiResponse.ApiError(ApiErrorType.PasswordDoesNotMatch)
        val viewModel = ChangePasswordViewModel(api)
        viewModel.oldPassword = "password"
        viewModel.password = "password123"
        viewModel.confirmPassword = "password123"
        Dispatchers.setMain(Dispatchers.Default)
        runTest {
            viewModel.onChange().join()
        }
        assertEquals("", viewModel.oldPassword)
        coVerify(exactly = 1) { api.change(any(), "password123") }
        confirmVerified(api)
    }

    @Test
    fun `ChangePasswordViewModel should clear new password on error`() {
        val api = mockk<PasswordApi>()
        coEvery { api.change(any(), any()) } returns ApiResponse.ApiError(ApiErrorType.IncorrectPassword)
        val viewModel = ChangePasswordViewModel(api)
        viewModel.password = "password123"
        viewModel.confirmPassword = "password123"
        Dispatchers.setMain(Dispatchers.Default)
        runTest {
            viewModel.onChange().join()
        }
        assertEquals("", viewModel.password)
        assertEquals("", viewModel.confirmPassword)
        coVerify(exactly = 1) { api.change(any(), "password123") }
        confirmVerified(api)
    }
}
