package com.target2sell.library.mapper

import com.target2sell.library.models.*
import com.target2sell.library.network.mapper.toRecommendationBody
import com.target2sell.library.network.mapper.toRecommendationHeader
import com.target2sell.library.network.mapper.toRequestRankParameters
import com.target2sell.library.network.mapper.toRequestTrackingParameters
import kotlin.test.Test
import kotlin.test.assertEquals

class Target2SellMapperExtensionTest {

    private val sessionId = "mySessionId"
    private val customerId = "myCustomerId"

    private val actualRecommendationParameters = RecommendationParameters(
        "myLocale",
        1,
        "myUserEmail",
        "myLanguage",
        "myItemId",
        "myBasketProducts",
        2,
        "myContextURL",
        "myKeywords",
        "mySetID",
        "myReferer",
        "myConstraint",
    )

    private val actualRankParameters = RankParameters(
        "MySetId2",
    )

    private val expectedRankParameters = RequestRankParameters(
        customerId,
        sessionId,
        "MySetId2",
    )

    private val actualTrackingParameters = TrackingParameters(
        1000,
        "myUserEmail",
        "myUserId",
        "myEventType",
        "mySpaceId",
        "myProductPosition",
        "myBasketProducts",
        "myLanguage",
        "myDomain",
        "myItemId",
        3,
        123,
        "myQuantity",
        "myKeywords",
        "myOrderID",
        "myPriceList",
        "myUserRank",
        "myCrm_XXX",
        "myAlgorithm",
        "myMediaRuleID",
        "myMediaCampaignId",
        "myMediaAlgo"
    )

    private val expectedTrackingParameters = RequestTrackingParameters(
        customerId,
        1000,
        sessionId,
        "myUserEmail",
        "myUserId",
        "myEventType",
        "mySpaceId",
        "myProductPosition",
        "myBasketProducts",
        "myLanguage",
        "myDomain",
        "myItemId",
        3,
        123,
        "myQuantity",
        "myKeywords",
        "myOrderID",
        "myPriceList",
        "myUserRank",
        "myCrm_XXX",
        "myMediaRuleID",
        "myAlgorithm",
        "myMediaCampaignId",
        "myMediaAlgo"
    )

    private val expectedHeaderRecommendation = HeaderRecommendation("myCustomerId", "myLocale")

    private val expectedBodyRecommendation = BodyRecommendation(
        1,
        sessionId = sessionId,
        "myUserEmail",
        "myLanguage",
        "myItemId",
        "myBasketProducts",
        2,
        "myContextURL",
        "myKeywords",
        "mySetID",
        "myReferer",
        "myConstraint",
    )

    @Test
    fun `assert function toRecommendationHeader succeeds`() {
        assertEquals(expectedHeaderRecommendation, actualRecommendationParameters.toRecommendationHeader(customerId))
    }

    @Test
    fun `assert function toRecommendationBody succeeds`() {
        assertEquals(expectedBodyRecommendation, actualRecommendationParameters.toRecommendationBody(sessionId))
    }

    @Test
    fun `assert function toRequestTrackingParameters succeeds`() {
        assertEquals(expectedTrackingParameters, actualTrackingParameters.toRequestTrackingParameters(sessionId, customerId))
    }

    @Test
    fun `assert function toRequestRankParameters succeeds`() {
        assertEquals(expectedRankParameters, actualRankParameters.toRequestRankParameters(sessionId, customerId))
    }

}