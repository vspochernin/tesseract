package ru.tesseract.portfolios.domain

import androidx.annotation.StringRes
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tesseract.R

enum class RiskLevel(
    @StringRes val resId: Int,
    @StringRes val explicitResId: Int,
) {
    High(R.string.risk_tolerance_high, R.string.risk_tolerance_high_risk),
    Medium(R.string.risk_tolerance_medium, R.string.risk_tolerance_medium_risk),
    Low(R.string.risk_tolerance_low, R.string.risk_tolerance_low_risk),
    Combined(R.string.risk_tolerance_combined, R.string.risk_tolerance_combined_risk),
}

@Serializable
data class Portfolio(
    @SerialName("portfolioId")
    val id: Int,
    @SerialName("createDatetime")
    val at: Instant,
    @SerialName("riskTypeId")
    val riskLevelOrdinal: Int,
    @SerialName("currentAmount")
    val currentAmount: Long,
    @SerialName("amountDiff")
    val amountDiff: Long,
)

val Portfolio.riskLevel get() = RiskLevel.entries[riskLevelOrdinal]
