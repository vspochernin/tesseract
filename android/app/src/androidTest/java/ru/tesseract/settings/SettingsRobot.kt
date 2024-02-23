package ru.tesseract.settings

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import ru.tesseract.waitUntilAtLeastOneExistsWithTag

class SettingsRobot(private val composeTestRule: ComposeTestRule) {
    fun navigateToTab() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("Navigation.Settings")
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
        waitUntilAtLeastOneExistsWithTag("SettingsScreen.ConfirmPasswordChange")
        onNodeWithTag("SettingsScreen.ConfirmPasswordChange").performClick()
    }

    fun assertSuccessDialogIsShown() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("SettingsScreen.SuccessDialog")
    }

    fun dismissSuccessDialog() = with(composeTestRule) {
        onNodeWithTag("SettingsScreen.DismissDialogButton").performClick()
    }

    fun logOut() = with(composeTestRule) {
        onNodeWithTag("SettingsScreen.LogOutButton").performClick()
    }

    fun waitUntilLoaded() = with(composeTestRule) {
        waitUntilAtLeastOneExistsWithTag("SettingsScreen.OldPassword")
    }

    fun tryToChangePassword(old: String, new: String, confirm: String = new) {
        navigateToTab()
        waitUntilLoaded()
        inputOldPassword(old)
        inputNewPassword(new)
        inputConfirmPassword(confirm)
        confirm()
    }
}
