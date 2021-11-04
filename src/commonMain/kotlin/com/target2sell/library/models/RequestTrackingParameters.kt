package com.target2sell.library.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RequestTrackingParameters (
    @SerialName("cID")
    val customerId: String,
    @SerialName("pID")
    val pageId: Int,
    @SerialName("tID")
    val sessionId: String,
    @SerialName("uEM")
    val userEmail: String? = null,
    @SerialName("uID")
    val userId: String? = null,
    @SerialName("eN")
    val eventType: String? = null,
    @SerialName("sp")
    val spaceId: String? = null,
    @SerialName("po")
    val productPosition: String? = null,
    @SerialName("bP")
    val basketProduct: String? = null,
    @SerialName("lang")
    val language: String? = null,
    @SerialName("domain")
    val domain: String? = null,
    @SerialName("iID")
    val itemId: String? = null,
    @SerialName("aID")
    val categoryId: Int? = null,
    @SerialName("bS")
    val cartTotalAmount: Int? = null,
    @SerialName("qTE")
    val productQuantity: String? = null,
    @SerialName("kW")
    val keywords: String? = null,
    @SerialName("oID")
    val orderId: String? = null,
    @SerialName("priceL")
    val priceList: String? = null,
    @SerialName("userRank")
    val userRank: String? = null,
    @SerialName("crm_XXX")
    val crm_XXX: String? = null,
    @SerialName("mediaRuleId")
    val mediaRuleId: String? = null,
    @SerialName("ru")
    val algorithm: String? = null,
    @SerialName("mediaCampaignId")
    val mediaCampaignId: String? = null,
    @SerialName("mediaAlgo")
    val mediaAlgo: String? = null,
    @SerialName("any")
    val any: String? = null
)