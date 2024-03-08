package ru.tesseract.portfolios.ui

import androidx.lifecycle.ViewModel
import org.koin.core.annotation.Factory
import ru.tesseract.portfolios.api.PortfoliosApi
import ru.tesseract.ui.paginate

@Factory
class PortfoliosViewModel(
    private val portfoliosApi: PortfoliosApi,
) : ViewModel() {
    val portfolios = paginate { portfoliosApi.getAll(it) }
}
