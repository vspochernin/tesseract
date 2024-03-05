package ru.tesseract.diversifications.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.koin.core.annotation.Factory
import ru.tesseract.api.onSuccess
import ru.tesseract.diversifications.api.DiversificationsApi
import ru.tesseract.diversifications.domain.DiversificationWithAssets

@Factory
class DiversificationViewModel(
    private val id: Int,
    private val diversificationsApi: DiversificationsApi,
) : ViewModel() {
    private var diversification by mutableStateOf<DiversificationWithAssets?>(null)

    @Composable
    fun diversification(): DiversificationWithAssets? {
        LaunchedEffect(Unit) {
            diversificationsApi.get(id).onSuccess {
                diversification = it
            }
        }
        return diversification
    }
}
