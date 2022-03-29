package com.target2sell.library.repository

import co.touchlab.kermit.Logger
import com.target2sell.library.Resource
import com.target2sell.library.error.T2SError
import com.target2sell.library.models.RankParameters
import com.target2sell.library.models.RecommendationParameters
import com.target2sell.library.models.TrackingParameters
import com.target2sell.library.service.Target2SellService
import com.target2sell.library.storage.Target2SellStorage

internal class Target2SellRepository(
    private val logger: Logger,
    private val service: Target2SellService,
    private val storage: Target2SellStorage
) {

    fun getUUID(): String? {
        return storage.getUUID()
    }

    fun getRank(): String {
        val rank = storage.getRank()
        return if (!rank.isNullOrEmpty()) {
            logger.d("Retrieving rank $rank from Preferences")
            rank
        } else {
            logger.d("Retrieving default Rank")
            defaultRankResult
        }
    }

    suspend fun retrieveAndStoreRank(rankParameters: RankParameters, customerId: String): Resource<String> {
        val uuid = storage.getUUID()
        logger.d("Processing getRank() call with parameters : $rankParameters and UUID : $uuid")
        return if (!uuid.isNullOrEmpty()) {
            when (val result = service.getRank(rankParameters, uuid, customerId)) {
                is Resource.Error -> {
                    logger.e("Can't process getRank() function, exception : ${result.error}")
                    Resource.Error(result.error)
                }
                is Resource.Success -> {
                    logger.d("Returning getRank() result : $result")
                    storage.putRank(result.result)
                    result
                }
            }
        } else {
            logger.e(T2SError.UUIDMissingError.toString())
            Resource.Error(T2SError.UUIDMissingError)
        }
    }


    suspend fun sendTracking(
        trackingParameters: TrackingParameters,
        userAgent: String,
        customerId: String
    ): Resource<String> {
        val uuid = storage.getUUID()
        logger.d("Processing sendTracking() call with parameters : $trackingParameters, userAgent : $userAgent and UUID : $uuid")

        return if (!uuid.isNullOrEmpty()) {
            when (val result = service.sendTracking(trackingParameters, userAgent, uuid, customerId)) {
                is Resource.Error -> {
                    logger.e("Can't process sendTracking() function, exception : ${result.error}")
                    Resource.Error(result.error)
                }
                is Resource.Success -> {
                    logger.d("Returning sendTracking() result : $result")
                    result
                }
            }
        } else {
            logger.e(T2SError.UUIDMissingError.toString())
            Resource.Error(T2SError.UUIDMissingError)
        }
    }

    suspend fun getRecommendations(
        recommendationParameters: RecommendationParameters,
        userAgent: String,
        customerId: String
    ): Resource<String> {
        val uuid = storage.getUUID()
        logger.d("Processing getRecommendations() call with parameters : $recommendationParameters, userAgent : $userAgent and UUID : $uuid")
        return if (!uuid.isNullOrEmpty()) {
            when (val result =
                service.getRecommendations(recommendationParameters, userAgent, uuid, customerId)) {
                is Resource.Error -> {
                    logger.e("Can't process getRecommendations() function, exception : ${result.error}")
                    Resource.Error(result.error)
                }
                is Resource.Success -> {
                    logger.d("Returning getRecommendations() result : $result")
                    result
                }
            }
        } else {
            logger.e(T2SError.UUIDMissingError.toString())
            Resource.Error(T2SError.UUIDMissingError)
        }
    }

    private companion object {
        const val defaultRankResult = "{\"rank\": \"rank1\"}"
    }
}
