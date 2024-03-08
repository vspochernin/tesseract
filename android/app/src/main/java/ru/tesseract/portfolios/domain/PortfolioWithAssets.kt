package ru.tesseract.portfolios.domain

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PortfolioWithAssets(
    @SerialName("createDateTime")
    val at: Instant,
    @SerialName("currentAmount")
    val currentAmount: Long,
    @SerialName("amountDiff")
    val amountDiff: Long,
    @SerialName("riskTypeId")
    val riskLevelOrdinal: Int,
    @SerialName("assetList")
    val assets: List<PortfolioAsset>,
)

@Serializable
data class PortfolioAsset(
    @SerialName("assetId")
    val id: Int,
    @SerialName("assetTitle")
    val title: String,
    @SerialName("companyTitle")
    val companyTitle: String,
    @SerialName("currentPriceSum")
    val currentPriceSum: Long,
    @SerialName("priceSumDiff")
    val priceSumDiff: Long,
    @SerialName("currentPrice")
    val currentPrice: Long,
    @SerialName("count")
    val count: Int,
    @SerialName("favouriteStatus")
    val isFavorite: Boolean = false,
)

val PortfolioWithAssets.riskLevel get() = RiskLevel.entries[riskLevelOrdinal]
