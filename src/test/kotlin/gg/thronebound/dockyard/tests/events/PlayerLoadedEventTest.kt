package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerLoadedEvent
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundPlayerLoadedPacket
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerLoadedEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<PlayerLoadedEvent> { count.countDown() }

        PlayerTestUtil.sendPacket(ServerboundPlayerLoadedPacket())

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}