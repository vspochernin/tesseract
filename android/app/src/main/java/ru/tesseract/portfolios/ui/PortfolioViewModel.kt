package ru.tesseract.portfolios.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.koin.core.annotation.Factory
import ru.tesseract.api.onSuccess
import ru.tesseract.portfolios.api.PortfoliosApi
import ru.tesseract.portfolios.domain.PortfolioWithAssets

@Factory
class PortfolioViewModel(
    private val id: Int,
    private val portfoliosApi: PortfoliosApi,
) : ViewModel() {
    private var portfolio by mutableStateOf<PortfolioWithAssets?>(null)

    @Composable
    fun portfolio(): PortfolioWithAssets? {
        LaunchedEffect(Unit) {
            portfoliosApi.get(id).onSuccess {
                portfolio = it
            }
        }
        return portfolio
    }
}
