package com.target2sell.library.expect

import platform.Foundation.NSUUID
import platform.UIKit.UIDevice

actual class Platform actual constructor() {

    actual fun platform() = platform

    actual fun generateUUID(): String {
        return NSUUID().UUIDString()
    }

    actual fun device() = UIDevice.currentDevice.name

    actual fun osVersion() = UIDevice.currentDevice.systemVersion

    companion object {
        const val platform = "iOS"
    }

}