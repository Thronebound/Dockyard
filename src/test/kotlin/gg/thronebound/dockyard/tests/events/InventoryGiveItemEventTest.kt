package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.InventoryGiveItemEvent
import gg.thronebound.dockyard.inventory.clearInventory
import gg.thronebound.dockyard.inventory.give
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.registry.Items
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class InventoryGiveItemEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<InventoryGiveItemEvent> {
            count.countDown()
        }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.give(Items.DEBUG_STICK)

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()

        player.clearInventory()
    }
}