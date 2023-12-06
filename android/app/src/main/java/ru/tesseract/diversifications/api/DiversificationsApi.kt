package ru.tesseract.diversifications.api

import org.koin.core.annotation.Single
import ru.tesseract.api.ApiClient
import ru.tesseract.api.ApiResponse
import ru.tesseract.api.page
import ru.tesseract.diversifications.domain.Diversification
import ru.tesseract.diversifications.domain.DiversificationWithAssets

@Single
class DiversificationsApi(
    private val client: ApiClient,
) {
    suspend fun getAll(page: Int): ApiResponse<List<Diversification>> =
        client.get("diversifications") { page(page) }

    suspend fun get(id: Int): ApiResponse<DiversificationWithAssets> =
        client.get("diversifications/$id")
}
