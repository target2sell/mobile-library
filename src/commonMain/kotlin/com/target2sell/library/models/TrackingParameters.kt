package com.target2sell.library.models

data class TrackingParameters (
    val pageId: Int,
    val userEmail: String? = null,
    val userId: String? = null,
    val eventType: String? = null,
    val spaceId: String? = null,
    val productPosition: String? = null,
    val basketProduct: String? = null,
    val language: String? = null,
    val domain: String? = null,
    val itemId: String? = null,
    val categoryId: Int? = null,
    val cartTotalAmount: Double? = null,
    val productQuantity: String? = null,
    val keywords: String? = null,
    val orderId: String? = null,
    val priceList: String? = null,
    val userRank: String? = null,
    val crm_XXX: String? = null,
    val algorithm: String? = null,
    val mediaRuleId: String? = null,
    val mediaCampaignId: String? = null,
    val mediaAlgo: String? = null,
    val any: String? = null,
    val trackingId: String? = null,
) {
    constructor(pageId: Int, trackingId: String): this(
        pageId,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        trackingId){}
}





