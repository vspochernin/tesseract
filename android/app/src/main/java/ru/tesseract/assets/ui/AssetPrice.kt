package ru.tesseract.assets.ui

fun formatPrice(value: Long) = "%.2f ₽".format(value.toDouble() / 100)
