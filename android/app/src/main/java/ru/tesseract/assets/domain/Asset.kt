package ru.tesseract.assets.domain

data class Asset(
    val id: Long,
    val name: String,
    val organization: String,
    val price: String,
    val isFavorite: Boolean,
)
