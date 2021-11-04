package com.target2sell.library.expect

import android.content.Context
import android.content.ContextWrapper

actual typealias Preferences = ContextWrapper

private const val preferencesName = "T2S_PREFERENCES"

actual fun Preferences.getString(key: String): String? {
    return this.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).getString(key, null)
}

actual fun Preferences.putString(key: String, value: String) {
    with(this.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).edit()) {
        putString(key, value)
        apply()
    }
}
