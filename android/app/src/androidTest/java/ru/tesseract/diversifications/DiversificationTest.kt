package ru.tesseract.diversifications

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.tesseract.errorhandling.ErrorRobot
import ru.tesseract.startLoggedIn

class DiversificationTest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()
    private val diversificationRobot = DiversificationRobot(composeTestRule)

    @Before
    fun start() = startLoggedIn()

    @Test
    fun givenValidAmount_whenCreateDiversification_thenDiversificationIsCreated() {
        diversificationRobot.tryToCreateDiversification("10000")
        diversificationRobot.assertDiversificationCount(2)
    }

    @Test
    fun givenAmountSmallerThanMinPrice_whenCreateDiversification_thenErrorDialogIsShown() {
        diversificationRobot.tryToCreateDiversification("1")
        ErrorRobot(composeTestRule).assertErrorDialogIsShown()
    }
}
