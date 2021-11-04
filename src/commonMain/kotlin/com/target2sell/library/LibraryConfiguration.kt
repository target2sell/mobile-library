package com.target2sell.library

import com.target2sell.library.expect.Preferences
import com.target2sell.library.models.Target2SellUserAgent

data class LibraryConfiguration(
    val context: Preferences,
    val customerId: String,
    val userAgent: Target2SellUserAgent,
    val enableCMP: Boolean = false,
    val displayDebugLogs: Boolean
)