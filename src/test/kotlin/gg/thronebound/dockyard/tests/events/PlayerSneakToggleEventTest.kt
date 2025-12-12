package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerSneakToggleEvent
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundClientInputPacket
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerSneakToggleEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(2)

        pool.on<PlayerSneakToggleEvent> {
            count.countDown()
        }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        PlayerTestUtil.sendPacket(player, ServerboundClientInputPacket(false, false, false, false, false, true, false))
        PlayerTestUtil.sendPacket(player, ServerboundClientInputPacket(false, false, false, false, false, false, false))

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}