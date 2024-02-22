package ru.tesseract.errorhandling

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick

@OptIn(ExperimentalTestApi::class)
class ErrorRobot(private val composeTestRule: ComposeTestRule) {
    fun assertErrorDialogIsShown() = with(composeTestRule) {
        waitUntilAtLeastOneExists(hasTestTag("ErrorHandling.Dialog"))
    }

    fun dismiss() = with(composeTestRule) {
        onNodeWithTag("ErrorHandling.DismissButton").performClick()
    }
}
