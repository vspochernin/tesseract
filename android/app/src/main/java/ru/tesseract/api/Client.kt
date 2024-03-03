package ru.tesseract.api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import ru.tesseract.KoverIgnore

@KoverIgnore
@Single
fun json(): Json = Json {
    isLenient = true
    ignoreUnknownKeys = true
}

@KoverIgnore
@Single
fun client(json: Json): HttpClient = HttpClient(Android) {
    install(ContentNegotiation) {
        json(json)
    }

    install(DefaultRequest) {
        header("Content-Type", "application/json")
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 10000
        connectTimeoutMillis = 10000
        socketTimeoutMillis = 10000
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
