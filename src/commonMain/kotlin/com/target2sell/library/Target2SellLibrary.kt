package com.target2sell.library

import com.target2sell.library.error.T2SError
import com.target2sell.library.expect.Platform
import com.target2sell.library.models.RankParameters
import com.target2sell.library.models.RecommendationParameters
import com.target2sell.library.models.TrackingParameters
import com.target2sell.library.module.ServiceLocator

class Target2SellLibrary internal constructor(
    libraryConfiguration: LibraryConfiguration,
    serviceLocator: ServiceLocator
) {
    constructor(libraryConfiguration: LibraryConfiguration) : this(
        libraryConfiguration,
        ServiceLocator(libraryConfiguration)
    )

    private val logger = serviceLocator.kermitLogger
    private val storage = serviceLocator.target2SellStorage
    private val repository = serviceLocator.target2SellRepository
    private val userAgent = libraryConfiguration.userAgent.formatUserAgent()
    private val customerId = libraryConfiguration.customerId
    private var enableCMP = libraryConfiguration.enableCMP

    init {
        generateAndStoreUUIDIfNotExists()
    }

    fun enableCmp() {
        enableCMP = true
    }

    fun disableCmp() {
        enableCMP = false
    }

    private fun generateAndStoreUUIDIfNotExists() {
        storage.putUUIDIfNotExists(Platform().generateUUID())
    }

    suspend fun useOrganiseModule(rankParameters: RankParameters): Resource<String> {
        return doIfCMPIsEnabled {
            repository.retrieveAndStoreRank(rankParameters, customerId)
        }
    }

    fun getRank(): String {
        return repository.getRank()
    }

    suspend fun sendTracking(trackingParameters: TrackingParameters): Resource<String> {
        return doIfCMPIsEnabled {
            repository.sendTracking(trackingParameters, userAgent, customerId)
        }
    }

    suspend fun getRecommendations(recommendationParameters: RecommendationParameters): Resource<String> {
        return doIfCMPIsEnabled {
            repository.getRecommendations(recommendationParameters, userAgent, customerId)
        }
    }

    private suspend fun <T> doIfCMPIsEnabled(block: suspend () -> Resource<T>): Resource<T> {
        return if (enableCMP) {
            block()
        } else {
            logger.e(T2SError.EnablerCMPError.toString())
            Resource.Error(T2SError.EnablerCMPError)
        }
    }

}
