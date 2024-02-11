package ru.tesseract.diversifications.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import ru.tesseract.api.onSuccess
import ru.tesseract.diversifications.api.DiversificationsApi
import ru.tesseract.diversifications.domain.RiskLevel
import ru.tesseract.ui.Validation

@Factory
class NewDiversificationViewModel(
    private val diversificationsApi: DiversificationsApi,
) : ViewModel() {
    var amountField by mutableStateOf("")
    var riskLevel by mutableStateOf(RiskLevel.Combined)
    private val isAmountValid by derivedStateOf { Validation.isDiversificationAmountValid(amountField) }
    var allowError by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    val showAmountError by derivedStateOf { allowError && !isAmountValid }
    private val isValid by derivedStateOf { isAmountValid }
    val isButtonEnabled by derivedStateOf { amountField.isNotEmpty() && !isLoading }

    fun onCreate(dismiss: suspend () -> Unit) = viewModelScope.launch {
        if (isLoading || !isValid) return@launch
        val amount = amountField.toLongOrNull() ?: return@launch
        val riskLevel = riskLevel
        isLoading = true
        diversificationsApi.create(amount * 100, riskLevel).onSuccess {
            dismiss()
        }
        isLoading = false
    }
}
