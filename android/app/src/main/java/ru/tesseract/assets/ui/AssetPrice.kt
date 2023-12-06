package ru.tesseract.assets.ui

import ru.tesseract.assets.domain.GeneralAssetInfo

fun GeneralAssetInfo.formattedPrice() = "%.2f ₽".format(price.toDouble() / 100)
