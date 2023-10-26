package ru.tesseract.assets.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import ru.tesseract.assets.domain.Asset

@ReadOnlyComposable
@Composable
fun Asset.annotatedPriceChange() =
    buildAnnotatedString {
        withStyle(
            SpanStyle(
                color =
                    when (change.first()) {
                        '+' -> lerp(Color.Green, MaterialTheme.colorScheme.primary, 0.3f)
                        '–' -> lerp(Color.Red, MaterialTheme.colorScheme.primary, 0.3f)
                        else -> error("Invalid change")
                    },
            ),
        ) {
            append(change)
        }
    }
