package com.target2sell.library.models

import com.target2sell.library.expect.Platform

data class Target2SellUserAgent internal constructor(
    private val applicationName: String,
    private val applicationVersion: String,
    private val applicationPackageName: String,
    private val applicationBuildVersion: String,
    private val platform: Platform
) {

    constructor(
        applicationName: String, applicationVersion: String,
        applicationPackageName: String, applicationBuildVersion: String
    ) : this(
        applicationName = applicationName,
        applicationVersion = applicationVersion,
        applicationPackageName = applicationPackageName,
        applicationBuildVersion = applicationBuildVersion,
        platform = Platform()
    )

    fun formatUserAgent(): String {
        return "$applicationName / $applicationVersion($applicationPackageName; " +
                "build:$applicationBuildVersion; ${platform.platform()} ${platform.osVersion()}) / ${platform.device()}"
    }
}