package com.target2sell.library.expect

import platform.Foundation.NSUserDefaults
import platform.darwin.NSObject

actual class Preferences : NSObject()

actual fun Preferences.getString(key: String): String? {
    return NSUserDefaults.standardUserDefaults.stringForKey(key)
}

actual fun Preferences.putString(key: String, value: String) {
    NSUserDefaults.standardUserDefaults.setObject(value, key)
    NSUserDefaults.standardUserDefaults.synchronize()
}