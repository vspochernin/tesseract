package ru.tesseract.assets.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import ru.tesseract.assets.domain.GeneralAssetInfo
import ru.tesseract.ui.asAnnotatedPriceDiff

@ReadOnlyComposable
@Composable
fun GeneralAssetInfo.annotatedPriceDiff() = priceDiff.asAnnotatedPriceDiff()
