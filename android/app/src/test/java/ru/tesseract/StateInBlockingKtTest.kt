package ru.tesseract

import app.cash.turbine.turbineScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class StateInBlockingKtTest {
    @Test
    fun `stateInBlocking should emit everything from flow`() = runTest {
        turbineScope {
            val expected = flowOf(1, 2, 3)
            val blocking = expected.stateInBlocking(backgroundScope)
            val turbine1 = expected.testIn(backgroundScope)
            val turbine2 = blocking.testIn(backgroundScope)
            assertEquals(turbine1.awaitItem(), turbine2.awaitItem())
            assertEquals(turbine1.awaitItem(), turbine2.awaitItem())
            assertEquals(turbine1.awaitItem(), turbine2.awaitItem())
            turbine1.awaitComplete()
            turbine2.cancel()
        }
    }
}
