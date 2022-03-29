package com.target2sell.library.expect

import com.target2sell.library.models.Target2SellUserAgent
import kotlin.test.Test
import kotlin.test.assertContains

class Target2SellUserAgentTest {

    @Test
    fun `assert formatUserAgent succeeds`() {
        val userAgent = Target2SellUserAgent("Mon application", "1.0", "com.client.package", "12345")
        assertContains(userAgent.formatUserAgent(), "Mon application / 1.0(com.client.package; build:12345; iOS")
    }
}