package ru.tesseract.api

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter

fun HttpRequestBuilder.page(value: Int): Unit =
    parameter("pageNumber", value)
