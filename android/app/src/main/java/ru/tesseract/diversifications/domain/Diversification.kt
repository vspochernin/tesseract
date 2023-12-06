package ru.tesseract.diversifications.domain

import androidx.annotation.StringRes
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

data class Diversification(
    val id: Long,
    val date: String,
    val riskLevel: RiskLevel,
    val sum: String,
)
