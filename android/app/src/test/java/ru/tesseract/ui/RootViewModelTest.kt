package ru.tesseract.ui

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import ru.tesseract.LoginState

class RootViewModelTest {
    @Test
    fun `RootViewModel isLoggedIn should return true if token exists`() = runTest {
        val loginState = mockk<LoginState>()
        every { loginState.token } returns MutableStateFlow("abc")
        val viewModel = RootViewModel(loginState)
        moleculeFlow(RecompositionMode.Immediate) {
            viewModel.isLoggedIn
        }.test {
            assertTrue(awaitItem())
        }
    }
}
