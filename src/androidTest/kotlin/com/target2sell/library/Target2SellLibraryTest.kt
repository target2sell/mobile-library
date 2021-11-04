package com.target2sell.library

import com.target2sell.library.error.T2SError
import com.target2sell.library.models.RankParameters
import com.target2sell.library.models.RecommendationParameters
import com.target2sell.library.models.TrackingParameters
import com.target2sell.library.module.ServiceLocator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

@Suppress("IllegalIdentifier")
class Target2SellLibraryTest {

    private lateinit var libraryConfiguration: LibraryConfiguration
    private lateinit var serviceLocator: ServiceLocator
    private lateinit var target2SellLibrary: Target2SellLibrary

    @BeforeTest
    fun init() {
        libraryConfiguration = mockk(relaxed = true)
        serviceLocator = mockk(relaxed = true)
        target2SellLibrary = Target2SellLibrary(libraryConfiguration, serviceLocator)
    }

    @Test
    fun `call useOrganiseModule with CMP Enabled succeeds`() {
        val rankResult = "Rank result"
        coEvery { serviceLocator.target2SellRepository.retrieveAndStoreRank(any(), any()) } returns Resource.Success(rankResult)

        runBlocking {
            target2SellLibrary.enableCmp()
            assertEquals(
                Resource.Success(rankResult),
                target2SellLibrary.useOrganiseModule(RankParameters(""))
            )
        }
    }


    @Test
    fun `call useOrganiseModule with CMP Disabled failed`() {
        target2SellLibrary.disableCmp()
        runBlocking {
            assertEquals(
                Resource.Error(T2SError.EnablerCMPError),
                target2SellLibrary.useOrganiseModule(RankParameters(""))
            )
        }
    }


    @Test
    fun `call getRecommendations with CMP Enabled succeeds`() {
        val recommendationResult = "Recommendation result"
        coEvery { serviceLocator.target2SellRepository.getRecommendations(any(), any(), any()) } returns Resource.Success(
            recommendationResult
        )

        target2SellLibrary.enableCmp()
        runBlocking {
            assertEquals(
                Resource.Success(recommendationResult),
                target2SellLibrary.getRecommendations(RecommendationParameters("", 1))
            )
        }
    }


    @Test
    fun `call getRecommendations with CMP Disabled failed`() {
        target2SellLibrary.disableCmp()
        runBlocking {
            assertEquals(
                Resource.Error(T2SError.EnablerCMPError),
                target2SellLibrary.getRecommendations(RecommendationParameters("", 1))
            )
        }
    }


    @Test
    fun `call sendTracking with CMP Enabled succeeds`() {
        val trackingResult = "Tracking result"
        coEvery { serviceLocator.target2SellRepository.sendTracking(any(), any(), any()) } returns Resource.Success(
            trackingResult
        )
        target2SellLibrary.enableCmp()
        runBlocking {
            assertEquals(
                Resource.Success(trackingResult),
                target2SellLibrary.sendTracking(TrackingParameters(1))
            )
        }
    }

    @Test
    fun `call sendTracking with CMP Disabled failed`() {
        target2SellLibrary.disableCmp()
        runBlocking {
            assertEquals(
                Resource.Error(T2SError.EnablerCMPError),
                target2SellLibrary.getRecommendations(RecommendationParameters("", 1))
            )
        }
    }
}
