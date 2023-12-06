package ru.tesseract.diversifications.api

import org.koin.core.annotation.Single
import ru.tesseract.api.ApiClient
import ru.tesseract.api.ApiResponse
import ru.tesseract.api.page
import ru.tesseract.diversifications.domain.Diversification

@Single
class DiversificationsApi(
    private val client: ApiClient,
) {
    suspend fun getAll(page: Int): ApiResponse<List<Diversification>> =
        client.get("diversifications") { page(page) }
}
