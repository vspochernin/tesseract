package ru.tesseract.portfolios.api

import io.ktor.client.request.setBody
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single
import ru.tesseract.KoverIgnore
import ru.tesseract.api.ApiClient
import ru.tesseract.api.ApiResponse
import ru.tesseract.api.page
import ru.tesseract.portfolios.domain.Portfolio
import ru.tesseract.portfolios.domain.PortfolioWithAssets
import ru.tesseract.portfolios.domain.RiskLevel

@Serializable
@KoverIgnore
private class CreateRequest(
    val amount: Long,
    val riskTypeId: Int,
)

@Single
@KoverIgnore
class PortfoliosApi(
    private val client: ApiClient,
) {
    suspend fun getAll(page: Int): ApiResponse<List<Portfolio>> =
        client.get("portfolios") { page(page) }

    suspend fun get(id: Int): ApiResponse<PortfolioWithAssets> =
        client.get("portfolios/$id")

    suspend fun create(amount: Long, riskLevel: RiskLevel): ApiResponse<Unit> =
        client.post("portfolios") { setBody(CreateRequest(amount, riskLevel.ordinal)) }
}
