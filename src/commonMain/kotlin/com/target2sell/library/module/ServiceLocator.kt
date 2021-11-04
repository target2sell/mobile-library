package com.target2sell.library.module

import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import com.target2sell.library.LibraryConfiguration
import com.target2sell.library.network.Target2SellApi
import com.target2sell.library.repository.Target2SellRepository
import com.target2sell.library.service.Target2SellService
import com.target2sell.library.storage.Target2SellStorage
import co.touchlab.kermit.Logger as KermitLogger

internal class ServiceLocator(private val libraryConfiguration: LibraryConfiguration) {

    val kermitLogger by lazy { initLogger(libraryConfiguration.displayDebugLogs)}
    val target2SellRepository by lazy { Target2SellRepository(kermitLogger, target2SellService, target2SellStorage) }
    val target2SellStorage by lazy { Target2SellStorage(kermitLogger, libraryConfiguration.context) }

    private val httpClient by lazy { makeClient(kermitLogger) }
    private val target2SellService by lazy { Target2SellService(target2SellApi) }
    private val target2SellApi by lazy { Target2SellApi(httpClient) }

    private fun initLogger(displayDebugLogs: Boolean): KermitLogger {
        val minSeverity = if (displayDebugLogs) Severity.Debug else Severity.Error
        return KermitLogger(StaticConfig(minSeverity, listOf(platformLogWriter()))).withTag(LibraryTAG)
    }

    companion object {
        private const val LibraryTAG = "T2SMobileLibrary"
    }
}