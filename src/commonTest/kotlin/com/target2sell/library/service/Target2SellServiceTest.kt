package com.target2sell.library.service

import com.target2sell.library.Resource
import com.target2sell.library.models.RankParameters
import com.target2sell.library.models.RecommendationParameters
import com.target2sell.library.models.TrackingParameters
import com.target2sell.library.network.Target2SellApi
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class Target2SellServiceTest {

    private lateinit var apiMockEngine: ApiMockEngine
    private lateinit var apiMock: Target2SellApi
    private lateinit var target2SellService: Target2SellService

    @BeforeTest
    fun init() {
        apiMockEngine = ApiMockEngine
        apiMock = Target2SellApi(ApiMockEngine.get())
        target2SellService = Target2SellService(apiMock)
    }

    @Test
    fun `getRank succeeds with ResourceSuccess value`() = runBlocking {
        val customerIdWithoutException = "customerId"
        val UUIDWithoutException = "Uuid"
        val rankParameters = RankParameters("TEST")
        assertTrue(
            target2SellService.getRank(
                rankParameters,
                UUIDWithoutException,
                customerIdWithoutException
            ) is Resource.Success
        )
    }

    @Test
    fun `getRank succeeds with ResourceError value`() = runBlocking {
        val rankParameters = RankParameters("TEST")
        val customerIdWithoutException = "customerId"
        val UUIDWithException = "UuidException"
        assertTrue(
            target2SellService.getRank(
                rankParameters,
                UUIDWithException,
                customerIdWithoutException
            ) is Resource.Error
        )
    }

    @Test
    fun `sendTracking succeeds with ResourceSuccess value`() = runBlocking {
        val userAgent = "UserAgent"
        val trackingParameters = TrackingParameters(
            pageId = 1000
        )
        val customerIdWithoutException = "customerId"
        val UUIDWithoutException = "Uuid"
        assertTrue(
            target2SellService.sendTracking(
                trackingParameters,
                userAgent,
                UUIDWithoutException,
                customerIdWithoutException
            ) is Resource.Success
        )
    }

    @Test
    fun `getRank succeeds with NoContent value`() = runBlocking {
        val rankParameters = RankParameters("TEST")
        val customerIdWithException = "exceptionId"
        val UUIDWithNoContent = "UuidNoContent"
        val defaultRankResponseContent = "{\"rank\": \"rank1\"}"
        assertTrue(
            target2SellService.getRank(
                rankParameters,
                UUIDWithNoContent,
                customerIdWithException
            ) is Resource.Success
        )
        assertEquals(
            target2SellService.getRank(rankParameters, UUIDWithNoContent, customerIdWithException),
            Resource.Success(defaultRankResponseContent)
        )
    }

    @Test
    fun `sendTracking succeeds with ResourceError value`() = runBlocking {
        val userAgent = "UserAgent"
        val trackingParameters = TrackingParameters(
            pageId = 1000
        )
        val customerIdWithException = "exceptionId"
        val UUIDWithoutException = "Uuid"
        assertTrue(
            target2SellService.sendTracking(
                trackingParameters,
                userAgent,
                UUIDWithoutException,
                customerIdWithException
            ) is Resource.Error
        )
    }

    @Test
    fun `getRecommendations succeeds with ResourceSuccess value`() = runBlocking {
        val userAgent = "UserAgent"
        val recommendationParameters = RecommendationParameters(
            locale = "fr",
            pageId = 1200,
        )
        val customerIdWithoutException = "customerId"
        val UUIDWithoutException = "Uuid"
        assertTrue(
            target2SellService.getRecommendations(
                recommendationParameters,
                userAgent,
                UUIDWithoutException,
                customerIdWithoutException
            ) is Resource.Success
        )
    }

    @Test
    fun `getRecommendations succeeds with ResourceError value`() = runBlocking {
        val userAgent = "UserAgent"
        val recommendationParameters = RecommendationParameters(
            locale = "fr",
            pageId = 1200
        )
        val customerIdWithException = "exceptionId"
        val UUIDWithoutException = "Uuid"
        assertTrue(
            target2SellService.getRecommendations(
                recommendationParameters,
                userAgent,
                UUIDWithoutException,
                customerIdWithException
            ) is Resource.Error
        )
    }

}
