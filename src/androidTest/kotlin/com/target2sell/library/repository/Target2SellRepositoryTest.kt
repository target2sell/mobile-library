package com.target2sell.library.repository

import co.touchlab.kermit.Logger
import com.target2sell.library.Resource
import com.target2sell.library.error.T2SError
import com.target2sell.library.models.RankParameters
import com.target2sell.library.models.RecommendationParameters
import com.target2sell.library.models.TrackingParameters
import com.target2sell.library.service.Target2SellService
import com.target2sell.library.storage.Target2SellStorage
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("IllegalIdentifier")
class Target2SellRepositoryTest {

    private lateinit var logger: Logger
    private lateinit var service: Target2SellService
    private lateinit var storage: Target2SellStorage
    private lateinit var repository: Target2SellRepository

    @BeforeTest
    fun init() {
        logger = mockk()
        service = mockk()
        storage = mockk()
        repository = Target2SellRepository(logger, service, storage)
        coEvery { storage.putRank(any()) } returns Unit
        coEvery { storage.putUUIDIfNotExists(any()) } returns Unit
        every { logger.e(any<String>()) } returns println()
        every { logger.d(any<String>()) } returns println()
    }

    @Test
    fun `retrieveAndStoreRank with missing UUID returns error`() {
        coEvery { storage.getUUID() } returns null
        runBlocking {
            assertEquals(
                Resource.Error(T2SError.UUIDMissingError),
                repository.retrieveAndStoreRank(RankParameters(""), "")
            )
        }
    }

    @Test
    fun `retrieveAndStoreRank with success service call succeeds`() {
        val resultRankFromService = "Rank service"
        coEvery { storage.getUUID() } returns "UUID"
        coEvery { service.getRank(any(), any(), any()) } returns Resource.Success(resultRankFromService)
        runBlocking {
            val result = repository.retrieveAndStoreRank(RankParameters(""), "")
            verify { storage.putRank(resultRankFromService) }
            assertEquals(Resource.Success(resultRankFromService), result)
        }
    }

    @Test
    fun `retrieveAndStoreRank with failed service call returns Error`() {
        coEvery { storage.getUUID() } returns "UUID"
        coEvery {
            service.getRank(
                any(),
                any(),
                any()
            )
        } returns Resource.Error(T2SError.DefaultError("Error"))
        runBlocking {
            assertEquals(
                Resource.Error(T2SError.DefaultError("Error")),
                repository.retrieveAndStoreRank(RankParameters(""), "")
            )
        }
    }

    @Test
    fun `getRecommendations with missing UUID returns error`() {
        coEvery { storage.getUUID() } returns null
        runBlocking {
            assertEquals(
                Resource.Error(T2SError.UUIDMissingError), repository.getRecommendations(
                    RecommendationParameters("", 1), "", ""
                )
            )
        }
    }

    @Test
    fun `getRecommendations with success service call succeeds`() {
        val resultRecommendation = "Recommendation result"
        coEvery { storage.getUUID() } returns "UUID"
        coEvery { service.getRecommendations(any(), any(), any(), any()) } returns Resource.Success(
            resultRecommendation
        )
        runBlocking {
            assertEquals(
                Resource.Success(resultRecommendation),
                repository.getRecommendations(
                    RecommendationParameters( "", 1), "", ""
                )
            )
        }
    }

    @Test
    fun `getRecommendations with failed service call returns error`() {
        coEvery { storage.getUUID() } returns "UUID"
        coEvery {
            service.getRecommendations(
                any(),
                any(),
                any(),
                any()
            )
        } returns Resource.Error(T2SError.DefaultError("Error"))
        runBlocking {
            assertEquals(
                Resource.Error(T2SError.DefaultError("Error")),
                repository.getRecommendations(
                    RecommendationParameters("", 1), "", ""
                )
            )
        }
    }

    @Test
    fun `sendTracking with missing UUID returns error`() {
        coEvery { storage.getUUID() } returns null
        runBlocking {
            assertEquals(
                Resource.Error(T2SError.UUIDMissingError), repository.sendTracking(
                    TrackingParameters(1), "", ""
                )
            )
        }
    }

    @Test
    fun `sendTracking with success service call succeeds`() {
        val resultTracking = "Tracking result"
        coEvery { storage.getUUID() } returns "UUID"
        coEvery { service.sendTracking(any(), any(), any(), any()) } returns Resource.Success(resultTracking)
        runBlocking {
            assertEquals(
                Resource.Success(resultTracking),
                repository.sendTracking(
                    TrackingParameters(1), "", ""
                )
            )
        }
    }

    @Test
    fun `sendTracking with failed service call returns error`() {
        coEvery { storage.getUUID() } returns "UUID"
        coEvery {
            service.sendTracking(
                any(),
                any(),
                any(),
                any()
            )
        } returns Resource.Error(T2SError.DefaultError("Error"))
        runBlocking {
            assertEquals(
                Resource.Error(T2SError.DefaultError("Error")),
                repository.sendTracking(
                    TrackingParameters(1), "", ""
                )
            )
        }
    }

    @Test
    fun `getRank with non empty storage returns result`() {
        val resultRankFromStorage = "Rank storage"
        every { storage.getRank() } returns resultRankFromStorage
        assertEquals(resultRankFromStorage, repository.getRank())
    }

    @Test
    fun `getRank With null storage returns default storage`() {
        val defaultRankResult = "{\"rank\": \"rank1\"}"
        every { storage.getRank() } returns null
        assertEquals(defaultRankResult, repository.getRank())
    }

    @Test
    fun `getRank with empty storage returns default storage`() {
        val defaultRankResult = "{\"rank\": \"rank1\"}"
        every { storage.getRank() } returns ""
        assertEquals(defaultRankResult, repository.getRank())
    }
}
