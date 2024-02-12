package ru.tesseract.assets.domain

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.*
import org.junit.Test
import ru.tesseract.diversifications.domain.RiskLevel

class DetailedAssetInfoSerializerTest {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val sample = DetailedAssetInfo(
        generalInfo = GeneralAssetInfo(
            id = 0,
            title = "Название",
            companyTitle = "Название компании",
            price = 12300L,
            priceDiff = 100L,
            isFavorite = false,
        ),
        description = "Описание",
        companyDescription = "Описание компании",
        operatorTitle = "Название оператора",
        riskLevel = RiskLevel.High
    )

    @Test
    fun `DetailedAssetInfo should be deserialized correctly`() {
        val serialized = """{
            "assetId": 0,
            "assetTitle": "Название",
            "companyTitle": "Название компании",
            "assetPrice": 12300,
            "assetPriceDiff": 100,
            "favouriteStatus": false,
            "assetDescription": "Описание",
            "companyDescription": "Описание компании",
            "operatorTitle": "Название оператора",
            "riskTypeId": 0
        }""".trimIndent()
        val deserialized = json.decodeFromString<DetailedAssetInfo>(serialized)
        assertEquals(sample, deserialized)
    }

    @Test
    fun `DetailedAssetInfo should be serialized correctly`() {
        val serialized = json.encodeToString(sample)
        assertEquals(sample, json.decodeFromString<DetailedAssetInfo>(serialized))
    }
}
