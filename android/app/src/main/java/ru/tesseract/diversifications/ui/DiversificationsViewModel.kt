package ru.tesseract.diversifications.ui

import androidx.lifecycle.ViewModel
import org.koin.core.annotation.Factory
import ru.tesseract.diversifications.api.DiversificationsApi
import ru.tesseract.ui.paginate

@Factory
class DiversificationsViewModel(
    private val diversificationsApi: DiversificationsApi,
) : ViewModel() {
    val diversifications = paginate { diversificationsApi.getAll(it) }
}
