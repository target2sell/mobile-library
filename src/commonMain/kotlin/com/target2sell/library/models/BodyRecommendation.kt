package com.target2sell.library.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BodyRecommendation(
    @SerialName("pID")
    val pageId: Int,
    @SerialName("tID")
    val sessionId: String,
    @SerialName("uEM")
    val userEmail: String? = null,
    @SerialName("lang")
    val language: String? = null,
    @SerialName("iID")
    val itemId: String? = null,
    @SerialName("bP")
    val basketProducts: String? = null,
    @SerialName("aId")
    val categoryId: Int? = null,
    @SerialName("cURL")
    val contextURL: String? = null,
    @SerialName("kW")
    val keywords: String? = null,
    @SerialName("setID")
    val setId: String? = null,
    @SerialName("rf")
    val referer: String? = null,
    @SerialName("constraint")
    val constraint: String? = null
)