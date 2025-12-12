package gg.thronebound.dockyard.tests.block.handlers

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.inventory.clearInventory
import kotlin.test.BeforeTest
import kotlin.test.Test

class DirectionalHandlerTest {

    @BeforeTest
    fun before() {
        TestServer.getOrSetupServer()
        PlayerTestUtil.getOrCreateFakePlayer().clearInventory()
        BlockHandlerTestUtil.reset()
    }

    @Test
    fun testPlaceOnSurface() {
    }
}