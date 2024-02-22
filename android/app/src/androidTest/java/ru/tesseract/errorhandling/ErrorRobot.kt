package ru.tesseract.errorhandling

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import ru.tesseract.waitUntilAtLeastOneExistsWithTag

class ErrorRobot(private val composeTestRule: ComposeTestRule) {
    fun assertErrorDialogIsShown() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("ErrorHandling.Dialog")
    }

    fun dismiss() = with(composeTestRule) {
        onNodeWithTag("ErrorHandling.DismissButton").performClick()
    }
}
