package com.target2sell.library.expect

import com.target2sell.library.models.Target2SellUserAgent
import kotlin.test.Test
import io.mockk.every
import io.mockk.spyk
import kotlin.test.assertEquals

class Target2SellUserAgentTest {

    private lateinit var platform: Platform
    @Test
    fun `assert formatUserAgent succeeds`() {
        platform = spyk()
        every { platform.osVersion() } returns "28"
        every { platform.device() } returns "Xiaomi A4GD6"

        val userAgent = Target2SellUserAgent("Mon application", "1.0", "com.client.package", "12345", platform)
        assertEquals(userAgent.formatUserAgent(), "Mon application / 1.0(com.client.package; build:12345; Android 28) / Xiaomi A4GD6")
    }
}