package com.target2sell.library.expect

expect class Preferences

expect fun Preferences.getString(key: String): String?
expect fun Preferences.putString(key: String, value: String)