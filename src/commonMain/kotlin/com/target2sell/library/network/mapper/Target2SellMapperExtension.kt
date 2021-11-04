package com.target2sell.library.network.mapper

import com.target2sell.library.models.*

internal fun RecommendationParameters.toRecommendationHeader(customerId: String) = HeaderRecommendation(
    customerId = customerId,
    locale = this.locale
)

internal fun RecommendationParameters.toRecommendationBody(uuid: String) = BodyRecommendation(
    pageId = this.pageId,
    sessionId = uuid,
    userEmail = this.userEmail,
    language = this.language,
    itemId = this.itemId,
    basketProducts = this.basketProducts,
    categoryId = this.categoryId,
    contextURL = this.contextURL,
    keywords = this.keywords,
    setId = this.setId,
    referer = this.referer,
    constraint = this.constraint
)

internal fun TrackingParameters.toRequestTrackingParameters(
    uuid: String,
    customerId: String
) = RequestTrackingParameters(
    customerId = customerId,
    pageId = this.pageId,
    sessionId = uuid,
    userEmail = this.userEmail,
    userId = this.userId,
    eventType = this.eventType,
    spaceId = this.spaceId,
    productPosition = this.productPosition,
    basketProduct = this.basketProduct,
    language = this.language,
    domain = this.domain,
    itemId = this.itemId,
    categoryId = this.categoryId,
    cartTotalAmount = this.cartTotalAmount,
    productQuantity = this.productQuantity,
    keywords = this.keywords,
    orderId = this.orderId,
    priceList = this.priceList,
    userRank = this.userRank,
    crm_XXX = this.crm_XXX,
    algorithm = this.algorithm,
    mediaRuleId = this.mediaRuleId,
    mediaCampaignId = this.mediaCampaignId,
    mediaAlgo = this.mediaAlgo,
    any = this.any,
)

internal fun RankParameters.toRequestRankParameters(uuid: String, customerId: String) = RequestRankParameters(
    cID = customerId,
    uuid = uuid,
    setId = this.setID
)
