package com.target2sell.library.module

import co.touchlab.kermit.Severity
import com.target2sell.library.LibraryConfiguration
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

@Suppress("IllegalIdentifier")
class ServiceLocatorTest {

    private lateinit var libraryConfiguration: LibraryConfiguration

    @BeforeTest
    fun init() {
        libraryConfiguration = mockk()
    }

    @Test
    fun `check Logger severity is debug`() {
        every { libraryConfiguration.displayDebugLogs } returns true
        val serviceLocator = ServiceLocator(libraryConfiguration)
        assertEquals(Severity.Debug, serviceLocator.kermitLogger.config.minSeverity)
    }

    @Test
    fun `check Logger severity is error`() {
        every { libraryConfiguration.displayDebugLogs } returns false
        val serviceLocator = ServiceLocator(libraryConfiguration)
        assertEquals(Severity.Error, serviceLocator.kermitLogger.config.minSeverity)
    }

    @Test
    fun `check Logger tag is correct`() {
        val libraryTag = "T2SMobileLibrary"
        every { libraryConfiguration.displayDebugLogs } returns false
        val serviceLocator = ServiceLocator(libraryConfiguration)
        assertEquals(libraryTag, serviceLocator.kermitLogger.tag)
    }
}