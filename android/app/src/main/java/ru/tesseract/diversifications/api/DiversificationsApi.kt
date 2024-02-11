package ru.tesseract.diversifications.api

import io.ktor.client.request.setBody
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single
import ru.tesseract.api.ApiClient
import ru.tesseract.api.ApiResponse
import ru.tesseract.api.page
import ru.tesseract.diversifications.domain.Diversification
import ru.tesseract.diversifications.domain.DiversificationWithAssets
import ru.tesseract.diversifications.domain.RiskLevel

@Serializable
private class CreateRequest(
    val amount: Long,
    val riskTypeId: Int,
)

@Single
class DiversificationsApi(
    private val client: ApiClient,
) {
    suspend fun getAll(page: Int): ApiResponse<List<Diversification>> =
        client.get("diversifications") { page(page) }

    suspend fun get(id: Int): ApiResponse<DiversificationWithAssets> =
        client.get("diversifications/$id")

    suspend fun create(amount: Long, riskLevel: RiskLevel): ApiResponse<Unit> =
        client.post("diversifications") { setBody(CreateRequest(amount, riskLevel.ordinal)) }
}
