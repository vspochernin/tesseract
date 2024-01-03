package ru.tesseract.diversifications.domain

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DiversificationWithAssets(
    @SerialName("createDateTime")
    val at: Instant,
    @SerialName("amount")
    val amount: Int,
    @SerialName("riskTypeId")
    val riskLevelOrdinal: Int,
    @SerialName("assetList")
    val assets: List<DiversificationAsset>,
)

@Serializable
class DiversificationAsset(
    @SerialName("assetId")
    val id: Int,
    @SerialName("assetTitle")
    val title: String,
    @SerialName("companyTitle")
    val companyTitle: String,
    @SerialName("oldPrice")
    val oldPrice: Int,
    @SerialName("count")
    val count: Int,
    @SerialName("favouriteStatus")
    val isFavorite: Boolean = false,
) {
    val sum: Int = oldPrice * count
}

val DiversificationWithAssets.riskLevel get() = RiskLevel.entries[riskLevelOrdinal]
