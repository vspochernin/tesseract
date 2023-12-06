package ru.tesseract.assets.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.koin.core.annotation.Factory
import ru.tesseract.api.onSuccess
import ru.tesseract.assets.api.AssetsApi
import ru.tesseract.assets.domain.DetailedAssetInfo

@Factory
class AssetViewModel(
    private val id: Int,
    private val assetsApi: AssetsApi,
) : ViewModel() {
    private var asset by mutableStateOf<DetailedAssetInfo?>(null)

    @Composable
    fun asset(): DetailedAssetInfo? {
        LaunchedEffect(Unit) {
            assetsApi.get(id).onSuccess {
                asset = it
            }
        }
        return asset
    }
}
