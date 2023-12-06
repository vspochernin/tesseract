package ru.tesseract.assets.ui

fun formatPrice(value: Int) = "%.2f ₽".format(value.toDouble() / 100)
