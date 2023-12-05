package ru.tesseract.assets.ui

import androidx.lifecycle.ViewModel
import org.koin.core.annotation.Factory
import ru.tesseract.assets.api.AssetsApi
import ru.tesseract.ui.paginate

@Factory
class FavoritesViewModel(
    private val assetsApi: AssetsApi,
) : ViewModel() {
    fun getAll() = paginate(assetsApi::getFavorites)
}
