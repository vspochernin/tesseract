package ru.tesseract.assets.api

import org.koin.core.annotation.Single
import ru.tesseract.KoverIgnore
import ru.tesseract.api.ApiClient
import ru.tesseract.api.ApiResponse
import ru.tesseract.api.page
import ru.tesseract.assets.domain.DetailedAssetInfo
import ru.tesseract.assets.domain.GeneralAssetInfo

@Single
@KoverIgnore
class AssetsApi(private val client: ApiClient) {
    suspend fun getAll(page: Int): ApiResponse<List<GeneralAssetInfo>> =
        client.get("assets") { page(page) }

    suspend fun get(id: Int): ApiResponse<DetailedAssetInfo> =
        client.get("assets/$id")

    suspend fun getFavorites(page: Int): ApiResponse<List<GeneralAssetInfo>> =
        client.get("favourites") { page(page) }

    suspend fun addFavorite(id: Int): ApiResponse<Unit> =
        client.post("favourites/$id")

    suspend fun removeFavorite(id: Int): ApiResponse<Unit> =
        client.delete("favourites/$id")
}
