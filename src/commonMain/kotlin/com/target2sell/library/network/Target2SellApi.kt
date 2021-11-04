package com.target2sell.library.network

import com.target2sell.library.models.BodyRecommendation
import com.target2sell.library.models.HeaderRecommendation
import com.target2sell.library.models.RequestRankParameters
import com.target2sell.library.models.RequestTrackingParameters
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*

internal class Target2SellApi(private val httpClient: HttpClient) {

    @Throws(IllegalStateException::class)
    suspend fun sendTracking(
        trackingParameters: RequestTrackingParameters,
        userAgent: String
    ): String {
        return httpClient.post<HttpResponse>(baseTrackingUrl) {
            trackingConfiguration(userAgent)
            body = FormDataContent(
                buildTrackingParameters(trackingParameters)
            )
        }.receive()
    }

    private fun buildTrackingParameters(trackingParameters: RequestTrackingParameters) =
        Parameters.build {
            trackingParameters.run {
                append(customerIdName, customerId)
                append(pageIdName, pageId.toString())
                append(sessionIdName, sessionId)

                userEmail?.let {
                    append(userEmailName, it)
                }
                userId?.let {
                    append(userIdName, it)
                }
                eventType?.let {
                    append(eventTypeName, it)
                }
                spaceId?.let {
                    append(spaceIdName, it)
                }
                productPosition?.let {
                    append(productPositionName, it)
                }
                basketProduct?.let {
                    append(basketProductName, it)
                }
                language?.let {
                    append(languageName, it)
                }
                domain?.let {
                    append(domainName, it)
                }
                itemId?.let {
                    append(itemIdName, it)
                }
                categoryId?.let {
                    append(categoryIdName, it.toString())
                }
                cartTotalAmount?.let {
                    append(cartTotalAmountName, it.toString())
                }
                productQuantity?.let {
                    append(productQuantityName, it)
                }
                algorithm?.let {
                    append(algorithmName, it)
                }
                keywords?.let {
                    append(keywordsName, it)
                }
                orderId?.let {
                    append(orderIdName, it)
                }
                priceList?.let {
                    append(priceListName, it)
                }
                userRank?.let {
                    append(userRankName, it)
                }
                crm_XXX?.let {
                    append(crm_XXXName, it)
                }
                mediaRuleId?.let {
                    append(mediaRuleIdName, it)
                }
                mediaCampaignId?.let {
                    append(mediaCampaignIdName, it)
                }
                mediaAlgo?.let {
                    append(mediaAlgoName, it)
                }
            }
        }

    @Throws(IllegalStateException::class)
    suspend fun getRecommendations(
        headerRecommendation: HeaderRecommendation,
        bodyRecommendation: BodyRecommendation,
        userAgent: String
    ): String {
        return httpClient.post<HttpResponse>("${baseRecommendationUrl}/${headerRecommendation.customerId}/${headerRecommendation.locale}") {
            recommendationConfiguration(userAgent)
            body = bodyRecommendation
        }.receive()
    }

    @Throws(IllegalStateException::class)
    suspend fun getRank(requestRankParameters: RequestRankParameters): String {
        val response =
            httpClient.get<HttpResponse>("${baseRankUrl}/${requestRankParameters.uuid}") {
                rankConfiguration(requestRankParameters.cID)
                requestRankParameters.setId?.let {
                    parameter("setId", it)
                }
            }

        return when (response.status) {
            HttpStatusCode.NoContent -> defaultRankResult
            else -> response.receive()
        }
    }

    private fun HttpRequestBuilder.rankConfiguration(customerId: String) {
        headers {
            append(t2sCustomerHeader, customerId)
        }
    }

    private fun HttpRequestBuilder.trackingConfiguration(userAgent: String) {
        headers {
            append(HttpHeaders.UserAgent, userAgent)
        }
    }

    private fun HttpRequestBuilder.recommendationConfiguration(userAgent: String) {
        headers {
            append(HttpHeaders.ContentType, ContentType.Application.Json)
            append(HttpHeaders.UserAgent, userAgent)
        }
    }

    companion object {
        private const val t2sCustomerHeader = "t2s-customer-id"
        private const val baseTrackingUrl = "https://serv-api.target2sell.com/1.1/json/T/t"
        private const val baseRecommendationUrl = "https://reco.target2sell.com/1.1/json/Q"
        private const val baseRankUrl = "https://api.target2sell.com/user/indexes"
        private const val customerIdName = "cID"
        private const val pageIdName = "pID"
        private const val sessionIdName = "tID"
        private const val userEmailName = "uEM"
        private const val userIdName = "uID"
        private const val eventTypeName = "eN"
        private const val spaceIdName = "sp"
        private const val productPositionName = "po"
        private const val basketProductName = "bP"
        private const val languageName = "lang"
        private const val domainName = "domain"
        private const val itemIdName = "iID"
        private const val categoryIdName = "aID"
        private const val cartTotalAmountName = "bS"
        private const val productQuantityName = "qTE"
        private const val keywordsName = "kW"
        private const val orderIdName = "oID"
        private const val priceListName = "priceL"
        private const val userRankName = "userRank"
        private const val crm_XXXName = "crm_XXX"
        private const val mediaRuleIdName = "mediaRuleId"
        private const val mediaCampaignIdName = "mediaCampaignId"
        private const val mediaAlgoName = "mediaAlgo"
        private const val algorithmName = "ru"
        private const val defaultRankResult = "{\"rank\": \"rank1\"}"
    }
}