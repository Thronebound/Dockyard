package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestFor
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerScreenCloseEvent
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundCloseContainerPacket
import gg.thronebound.dockyard.ui.TestScreen
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@TestFor(PlayerScreenCloseEvent::class)
class PlayerScreenCloseEventTest {

    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<PlayerScreenCloseEvent> { count.countDown() }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        val screen = TestScreen()

        screen.open(player)
        PlayerTestUtil.sendPacket(player, ServerboundCloseContainerPacket(1))

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
        screen.dispose()
    }
}