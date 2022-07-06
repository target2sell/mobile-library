package com.target2sell.library.service

import com.target2sell.library.Resource
import com.target2sell.library.error.toT2SError
import com.target2sell.library.models.RankParameters
import com.target2sell.library.models.RecommendationParameters
import com.target2sell.library.models.TrackingParameters
import com.target2sell.library.network.Target2SellApi
import com.target2sell.library.network.mapper.toRecommendationBody
import com.target2sell.library.network.mapper.toRecommendationHeader
import com.target2sell.library.network.mapper.toRequestRankParameters
import com.target2sell.library.network.mapper.toRequestTrackingParameters

internal class Target2SellService(private val api: Target2SellApi) {

    suspend fun getRank(rankParameters: RankParameters, uuid: String, customerId: String): Resource<String> {
        return try {
            Resource.Success(api.getRank(rankParameters.toRequestRankParameters(uuid, customerId)))
        } catch (e: Exception) {
            Resource.Error(e.toT2SError())
        }
    }

    suspend fun sendTracking(
        trackingParameters: TrackingParameters,
        userAgent: String,
        uuid: String,
        customerId: String
    ): Resource<String> {
        return try {
            Resource.Success(
                api.sendTracking(
                    trackingParameters.toRequestTrackingParameters(uuid, customerId),
                    userAgent
                )
            )
        } catch (e: Exception) {
            Resource.Error(e.toT2SError())
        }
    }

    suspend fun getRecommendations(
        recommendationParameters: RecommendationParameters,
        userAgent: String,
        uuid: String,
        customerId: String
    ): Resource<String> {
        return try {
            val header = recommendationParameters.toRecommendationHeader(customerId)
            val body = recommendationParameters.toRecommendationBody(uuid)
            Resource.Success(api.getRecommendations(header, body, userAgent))
        } catch (e: Exception) {
            Resource.Error(e.toT2SError())
        }
    }
}