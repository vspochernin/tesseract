package ru.tesseract.assets.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeneralAssetInfo(
    @SerialName("assetId")
    val id: Int,
    @SerialName("assetTitle")
    val title: String,
    @SerialName("companyTitle")
    val companyTitle: String,
    @SerialName("assetPrice")
    val price: Long,
    @SerialName("assetPriceDiff")
    val priceDiff: Long,
    @SerialName("favouriteStatus")
    val isFavorite: Boolean,
)
