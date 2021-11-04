package com.target2sell.library.storage

import co.touchlab.kermit.Logger
import com.target2sell.library.expect.Preferences
import com.target2sell.library.expect.getString
import com.target2sell.library.expect.putString

internal class Target2SellStorage(private val logger: Logger, private val context: Preferences) {

    fun getUUID(): String? {
        return context.getString(UUID)
    }

    fun putUUIDIfNotExists(value: String) {
        if (getUUID().isNullOrEmpty()) {
            logger.d("Storing UUID : $value")
            context.putString(UUID, value)
        }
    }

    fun getRank(): String? {
        return context.getString(Rank)
    }

    fun putRank(value: String) {
        logger.d("Storing Rank : $value")
        context.putString(Rank, value)
    }

    companion object {
        const val UUID = "UUID"
        const val Rank = "Rank"
    }
}