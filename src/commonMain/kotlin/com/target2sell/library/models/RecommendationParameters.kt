package com.target2sell.library.models

data class RecommendationParameters(
    val locale: String,
    val pageId: Int,
    val userEmail: String? = null,
    val language: String? = null,
    val itemId: String? = null,
    val basketProducts: String? = null,
    val categoryId: Int? = null,
    val contextURL: String? = null,
    val keywords: String? = null,
    val setId: String? = null,
    val referer: String? = null,
    val constraint: String? = null
)