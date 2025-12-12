package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerPickItemFromBlockEvent
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundPickItemFromBlockPacket
import gg.thronebound.dockyard.math.vectors.Vector3
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerPickItemFromBlockEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<PlayerPickItemFromBlockEvent> {count.countDown()}

        PlayerTestUtil.sendPacket(ServerboundPickItemFromBlockPacket(Vector3(0), false))
        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}