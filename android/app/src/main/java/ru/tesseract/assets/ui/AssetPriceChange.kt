package ru.tesseract.assets.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import ru.tesseract.assets.domain.GeneralAssetInfo

@ReadOnlyComposable
@Composable
fun GeneralAssetInfo.annotatedPriceDiff() =
    buildAnnotatedString {
        val diff = "%.2f".format(priceDiff.toDouble() / 100)
        val diffString = (if (priceDiff > 0) "+" else "") + diff
        withStyle(
            SpanStyle(
                color =
                    when (diffString.first()) {
                        '+' -> lerp(Color.Green, MaterialTheme.colorScheme.primary, 0.3f)
                        '-' -> lerp(Color.Red, MaterialTheme.colorScheme.primary, 0.3f)
                        else -> MaterialTheme.colorScheme.onBackground
                    },
            ),
        ) {
            append(diffString)
        }
    }
