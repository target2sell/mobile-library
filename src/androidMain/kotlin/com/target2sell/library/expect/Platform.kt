package com.target2sell.library.expect

import android.os.Build
import java.util.UUID

actual class Platform actual constructor() {

    actual fun platform() = platform

    actual fun generateUUID(): String {
        return UUID.randomUUID().toString()
    }

    actual fun device() = "${Build.MANUFACTURER} ${Build.MODEL}"

    actual fun osVersion() = Build.VERSION.SDK_INT.toString()

    companion object {
        const val platform = "Android"
    }
}