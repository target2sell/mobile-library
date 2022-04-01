package com.target2sell.library

import com.target2sell.library.expect.Preferences
import com.target2sell.library.models.RankParameters
import com.target2sell.library.models.Target2SellUserAgent
import com.target2sell.library.network.Target2SellApi
import com.target2sell.library.service.ApiMockEngine
import com.target2sell.library.service.Target2SellService
import kotlinx.coroutines.runBlocking
import platform.darwin.NSObject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Target2SellLibraryTest {
    private lateinit var apiMockEngine: ApiMockEngine
    private lateinit var apiMock: Target2SellApi
    private lateinit var target2SellService: Target2SellService
    private lateinit var configuration: LibraryConfiguration
    @BeforeTest
    fun init() {
        apiMockEngine = ApiMockEngine
        apiMock = Target2SellApi(ApiMockEngine.get())
        target2SellService = Target2SellService(apiMock)
        val userAgent = Target2SellUserAgent("","","", "")
        configuration = LibraryConfiguration(Preferences(), "", userAgent, true, true)
    }

    @Test
    fun `get uuid`() = runBlocking {
        val customerIdWithoutException = "customerId"
        val UUIDWithoutException = "Uuid"
        val rankParameters = RankParameters("TEST")
        var sut = Target2SellLibrary(configuration)
        assertTrue(
            sut.getUUID()!!.length >= 20
        )
    }
}