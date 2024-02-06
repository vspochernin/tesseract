package ru.tesseract.diversifications.domain

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DiversificationWithAssets(
    @SerialName("createDateTime")
    val at: Instant,
    @SerialName("currentAmount")
    val currentAmount: Int,
    @SerialName("amountDiff")
    val amountDiff: Int,
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
    @SerialName("currentPriceSum")
    val currentPriceSum: Int,
    @SerialName("priceSumDiff")
    val priceSumDiff: Int,
    @SerialName("currentPrice")
    val currentPrice: Int,
    @SerialName("count")
    val count: Int,
    @SerialName("favouriteStatus")
    val isFavorite: Boolean = false,
)

val DiversificationWithAssets.riskLevel get() = RiskLevel.entries[riskLevelOrdinal]
