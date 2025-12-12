package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.CommandSuggestionEvent
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundCommandSuggestionPacket
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class CommandSuggestionEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<CommandSuggestionEvent> {
            count.countDown()
        }

        PlayerTestUtil.sendPacket(ServerboundCommandSuggestionPacket(0, "/hel"))

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}