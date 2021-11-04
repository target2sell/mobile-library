package com.target2sell.library.service

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

object ApiMockEngine {
    fun get() = client

    private val responseHeaders =
        headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))

    private val client = HttpClient(MockEngine) {
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    urlRankWithoutException -> {
                        respond("", HttpStatusCode.OK, responseHeaders)
                    }
                    urlRankWithException -> {
                        respond("", HttpStatusCode.BadRequest, responseHeaders)
                    }
                    urlRankWithNoContent -> {
                        respond(defaultRankResponseContent, HttpStatusCode.NoContent, responseHeaders)
                    }
                    urlRecommendationWithoutException -> {
                        respond("", HttpStatusCode.OK, responseHeaders)
                    }
                    urlRecommendationWithException -> {
                        respond("", HttpStatusCode.BadRequest, responseHeaders)
                    }
                    urlTrackingWithException -> {
                        if (((request.body) as FormDataContent).formData["cID"] == customerIdWithException) {
                            respond("", HttpStatusCode.BadRequest, responseHeaders)
                        } else {
                            respond("", HttpStatusCode.OK, responseHeaders)
                        }
                    }
                    else -> {
                        error("Unhandled ${request.url.encodedPath}")
                    }
                }
            }
        }
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    private const val customerIdWithException = "exceptionId"
    private const val customerIdWithoutException = "customerId"
    private const val UUIDWithoutException = "Uuid"
    private const val UUIDWithException = "UuidException"
    private const val UUIDWithNoContent = "UuidNoContent"
    private const val urlRankWithoutException = "/user/indexes/$UUIDWithoutException"
    private const val urlRankWithException = "/user/indexes/$UUIDWithException"
    private const val urlRankWithNoContent = "/user/indexes/$UUIDWithNoContent"
    private const val urlRecommendationWithoutException = "/1.1/json/Q/$customerIdWithoutException/fr"
    private const val urlRecommendationWithException = "/1.1/json/Q/$customerIdWithException/fr"
    private const val urlTrackingWithException = "/1.1/json/T/t"
    private const val defaultRankResponseContent = "{\"rank\": \"rank1\"}"
}
