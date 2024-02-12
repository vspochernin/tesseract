package ru.tesseract.diversifications.domain

import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.*
import org.junit.Test

class DiversificationTest {
    @Test
    fun `DiversificationWithAssets should serialize correctly`() {
        val sampleDiversificationWithAssets = DiversificationWithAssets(
            at = Clock.System.now(),
            currentAmount = 12300,
            amountDiff = 100,
            riskLevelOrdinal = 0,
            assets = listOf(
                DiversificationAsset(
                    id = 1,
                    title = "title",
                    companyTitle = "company title",
                    currentPriceSum = 12300,
                    priceSumDiff = 100,
                    currentPrice = 12300,
                    count = 1,
                    isFavorite = false
                )
            )
        )
        val serialized = Json.encodeToString(sampleDiversificationWithAssets)
        assertEquals(sampleDiversificationWithAssets, Json.decodeFromString<DiversificationWithAssets>(serialized))
    }

    @Test
    fun `Diversification should serialize correctly`() {
        val sampleDiversification = Diversification(
            id = 1,
            at = Clock.System.now(),
            riskLevelOrdinal = 0,
            currentAmount = 12300,
            amountDiff = 100,
        )
        val serialized = Json.encodeToString(sampleDiversification)
        assertEquals(sampleDiversification, Json.decodeFromString<Diversification>(serialized))
    }
}
