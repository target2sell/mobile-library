package com.target2sell.library.module

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import co.touchlab.kermit.Logger as KermitLogger

internal fun makeClient(kermitLogger: KermitLogger) : HttpClient {
    return HttpClient {
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    kermitLogger.d(message = message)
                }
            }
        }
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }
}
