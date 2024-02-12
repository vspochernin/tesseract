package ru.tesseract.api

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import ru.tesseract.KoverIgnore

@KoverIgnore
fun HttpRequestBuilder.page(value: Int): Unit =
    parameter("pageNumber", value)
