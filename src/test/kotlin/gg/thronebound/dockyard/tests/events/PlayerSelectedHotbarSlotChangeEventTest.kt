package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerDropItemEvent
import gg.thronebound.dockyard.events.PlayerSelectedHotbarSlotChangeEvent
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundSetPlayerHeldItemPacket
import gg.thronebound.dockyard.registry.Items
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerSelectedHotbarSlotChangeEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        val player = PlayerTestUtil.getOrCreateFakePlayer()

        pool.on<PlayerSelectedHotbarSlotChangeEvent> {
            count.countDown()
        }

        PlayerTestUtil.sendPacket(
            player,
            ServerboundSetPlayerHeldItemPacket((player.heldSlotIndex.value + 1) % 9)
        )

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}