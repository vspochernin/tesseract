package ru.tesseract.assets.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GeneralAssetInfo(
    @SerialName("assetId")
    val id: Int,
    @SerialName("assetTitle")
    val title: String,
    @SerialName("companyTitle")
    val companyTitle: String,
    @SerialName("assetPrice")
    val price: Int,
    @SerialName("assetPriceDiff")
    val priceDiff: Int,
    @SerialName("favouriteStatus")
    val isFavorite: Boolean,
)
