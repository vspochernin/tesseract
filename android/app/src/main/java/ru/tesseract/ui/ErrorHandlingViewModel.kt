package ru.tesseract.ui

import androidx.lifecycle.ViewModel
import org.koin.core.annotation.Factory
import ru.tesseract.api.ApiClient

@Factory
class ErrorHandlingViewModel(client: ApiClient) : ViewModel() {
    val apiErrors = client.apiErrors
    val networkErrors = client.networkErrors
}
