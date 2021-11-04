package com.target2sell.library.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class HeaderRecommendation(
    @SerialName("cID")
    val customerId: String,
    @SerialName("locale")
    val locale: String,
)