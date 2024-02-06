package ru.tesseract.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@ReadOnlyComposable
@Composable
fun Int.asAnnotatedPriceDiff() =
    buildAnnotatedString {
        val diff = "%.2f ₽".format(this@asAnnotatedPriceDiff.toDouble() / 100)
        val diffString = (if (this@asAnnotatedPriceDiff >= 0) "+" else "") + diff
        withStyle(
            SpanStyle(
                color =
                when {
                    this@asAnnotatedPriceDiff > 0 -> lerp(Color.Green, MaterialTheme.colorScheme.primary, 0.3f)
                    this@asAnnotatedPriceDiff < 0 -> lerp(Color.Red, MaterialTheme.colorScheme.primary, 0.3f)
                    else -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                },
            ),
        ) {
            append(diffString)
        }
    }
