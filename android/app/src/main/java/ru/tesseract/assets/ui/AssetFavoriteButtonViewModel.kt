package ru.tesseract.assets.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import org.koin.core.annotation.Factory
import ru.tesseract.api.onFailure
import ru.tesseract.assets.api.AssetsApi

@Factory
class AssetFavoriteButtonViewModel(
    private val id: Int,
    initialIsFavorite: Boolean,
    private val assetsApi: AssetsApi,
) : ViewModel() {
    private var optimisticIsFavorite by mutableStateOf(initialIsFavorite)
    private val lock = Mutex()

    val isFavorite @Composable get() = optimisticIsFavorite

    fun onClickFavorite() = viewModelScope.launch {
        if (lock.tryLock().not()) return@launch
        optimisticIsFavorite = optimisticIsFavorite.not()
        if (optimisticIsFavorite) {
            assetsApi.addFavorite(id)
        } else {
            assetsApi.removeFavorite(id)
        }.onFailure {
            optimisticIsFavorite = optimisticIsFavorite.not()
        }
        lock.unlock()
    }

}
