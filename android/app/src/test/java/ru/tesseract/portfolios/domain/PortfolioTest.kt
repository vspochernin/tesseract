package ru.tesseract.portfolios.domain

import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class PortfolioTest {
    @Test
    fun `PortfolioWithAssets should serialize correctly`() {
        val expected = PortfolioWithAssets(
            at = Clock.System.now(),
            currentAmount = 12300,
            amountDiff = 100,
            riskLevelOrdinal = 0,
            assets = listOf(
                PortfolioAsset(
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
        val serialized = Json.encodeToString(expected)
        val deserialized = Json.decodeFromString<PortfolioWithAssets>(serialized)
        assertEquals(expected.at, deserialized.at)
        assertEquals(expected.currentAmount, deserialized.currentAmount)
        assertEquals(expected.amountDiff, deserialized.amountDiff)
        assertEquals(expected.riskLevelOrdinal, deserialized.riskLevelOrdinal)
        assertEquals(expected.assets[0].id, deserialized.assets[0].id)
        assertEquals(expected.assets[0].title, deserialized.assets[0].title)
        assertEquals(expected.assets[0].companyTitle, deserialized.assets[0].companyTitle)
        assertEquals(expected.assets[0].currentPriceSum, deserialized.assets[0].currentPriceSum)
        assertEquals(expected.assets[0].priceSumDiff, deserialized.assets[0].priceSumDiff)
        assertEquals(expected.assets[0].currentPrice, deserialized.assets[0].currentPrice)
        assertEquals(expected.assets[0].count, deserialized.assets[0].count)
        assertEquals(expected.assets[0].isFavorite, deserialized.assets[0].isFavorite)
    }

    @Test
    fun `Portfolio should serialize correctly`() {
        val samplePortfolio = Portfolio(
            id = 1,
            at = Clock.System.now(),
            riskLevelOrdinal = 0,
            currentAmount = 12300,
            amountDiff = 100,
        )
        val serialized = Json.encodeToString(samplePortfolio)
        assertEquals(samplePortfolio, Json.decodeFromString<Portfolio>(serialized))
    }
}
