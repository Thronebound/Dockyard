package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerChatMessageEvent
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundPlayerChatMessagePacket
import kotlinx.datetime.Clock
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerChatMessageEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<PlayerChatMessageEvent> {
            count.countDown()
        }

        PlayerTestUtil.sendPacket(ServerboundPlayerChatMessagePacket("I <3 Dockyard", Clock.System.now(), 3, null, 0, null, 1))

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}