package ru.tesseract.api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

@Single
fun json(): Json = Json {
    isLenient = true
    ignoreUnknownKeys = true
}

@Single
fun client(json: Json): HttpClient = HttpClient(Android) {
    install(ContentNegotiation) {
        json(json)
    }

    install(DefaultRequest) {
        header("Content-Type", "application/json")
        url("http://10.0.2.2:8080/api/v1/")
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.d("Ktor", message)
            }
        }
        level = LogLevel.ALL
    }
}
