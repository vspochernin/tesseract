package ru.tesseract.assets.domain

import ru.tesseract.diversifications.domain.RiskTolerance

data class Asset(
    val id: Long,
    val name: String,
    val organization: String,
    val price: String,
    val change: String,
    val riskTolerance: RiskTolerance,
    val isFavorite: Boolean,
)
