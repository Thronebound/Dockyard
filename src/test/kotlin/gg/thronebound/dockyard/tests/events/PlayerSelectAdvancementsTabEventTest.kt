package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerSelectAdvancementsTabEvent
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundSelectAdvancementsTabPacket
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.assertTrue

class PlayerSelectAdvancementsTabEventTest {
    @BeforeEach
    fun setUp() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<PlayerSelectAdvancementsTabEvent> {
            if(it.tabId == "test") {
                count.countDown()
                it.cancelled = true
            }
        }

        PlayerTestUtil.sendPacket(
            ServerboundSelectAdvancementsTabPacket(
                ServerboundSelectAdvancementsTabPacket.Action.OPENED_TAB,
                "test"
            )
        )

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }

}