package org.mohsen.reviewtask.data.source.remote

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.io.IOException
import kotlinx.serialization.json.Json

private const val BASE_URL = "api.example.com"

fun provideKtorClient(): HttpClient =
    HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                    explicitNulls = false
                    coerceInputValues = true
                    allowSpecialFloatingPointValues = true
                })
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
            }
        }


        install(HttpTimeout) {
            requestTimeoutMillis = 20_000
            connectTimeoutMillis = 20_000
            socketTimeoutMillis = 20_000
        }

        install(HttpRequestRetry) {
            maxRetries = 3
            retryOnExceptionIf { request, cause ->
                cause is HttpRequestTimeoutException || cause is IOException
            }
            delayMillis { retry -> retry * 1000L }
        }
    }
