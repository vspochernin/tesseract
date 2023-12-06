package ru.tesseract.diversifications.domain

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
data class Diversification(
    @SerialName("diversificationId")
    val id: Int,
    @SerialName("createDatetime")
    val at: Instant,
    @SerialName("riskTypeId")
    val riskLevelOrdinal: Int,
    @SerialName("amount")
    val amount: Int,
)

val Diversification.riskLevel get() = RiskLevel.entries[riskLevelOrdinal]
