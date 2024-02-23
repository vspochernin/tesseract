package ru.tesseract

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule

private const val TimeoutMillis = 10_000L

@OptIn(ExperimentalTestApi::class)
fun ComposeTestRule.waitUntilDoesNotExistWithTag(tag: String) {
    waitUntilDoesNotExist(hasTestTag(tag), TimeoutMillis)
}

@OptIn(ExperimentalTestApi::class)
fun ComposeTestRule.waitUntilAtLeastOneExistsWithTag(tag: String) {
    waitUntilAtLeastOneExists(hasTestTag(tag), TimeoutMillis)
}
