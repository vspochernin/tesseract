package ru.tesseract.assets

import androidx.compose.ui.test.junit4.ComposeTestRule
import ru.tesseract.waitUntilDoesNotExistWithTag

class PaginationRobot(private val composeTestRule: ComposeTestRule) {
    fun waitUntilLoaded() = with(composeTestRule) {
        waitUntilDoesNotExistWithTag("Pagination.Loading")
    }
}
