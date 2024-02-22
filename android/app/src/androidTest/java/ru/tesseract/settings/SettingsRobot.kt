package ru.tesseract.settings

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import ru.tesseract.waitUntilAtLeastOneExistsWithTag

class SettingsRobot(private val composeTestRule: ComposeTestRule) {
    fun navigateToTab() = with(composeTestRule) {
        onNodeWithTag("Navigation.Settings").performClick()
    }

    fun inputOldPassword(text: String) = with(composeTestRule) {
        onNodeWithTag("SettingsScreen.OldPassword").performTextInput(text)
    }

    fun inputNewPassword(text: String) = with(composeTestRule) {
        onNodeWithTag("SettingsScreen.NewPassword").performTextInput(text)
    }

    fun inputConfirmPassword(text: String) = with(composeTestRule) {
        onNodeWithTag("SettingsScreen.ConfirmPassword").performTextInput(text)
    }

    fun confirm() = with(composeTestRule) {
        onNodeWithTag("SettingsScreen.ConfirmPasswordChange").performClick()
    }

    fun assertSuccessDialogIsShown() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("SettingsScreen.SuccessDialog")
    }

    fun logOut() = with(composeTestRule) {
        onNodeWithTag("SettingsScreen.LogOutButton").performClick()
    }

    fun tryToChangePassword(old: String, new: String, confirm: String = new) {
        navigateToTab()
        inputOldPassword(old)
        inputNewPassword(new)
        inputConfirmPassword(confirm)
        confirm()
    }
}
