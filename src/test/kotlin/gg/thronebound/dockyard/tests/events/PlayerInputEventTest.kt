package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestFor
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerInputEvent
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundClientInputPacket
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@TestFor(PlayerInputEvent::class)
class PlayerInputEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(2)

        val player = PlayerTestUtil.getOrCreateFakePlayer()

        pool.on<PlayerInputEvent> {
            count.countDown()
        }

        PlayerTestUtil.sendPacket(
            player,
            ServerboundClientInputPacket(
                forward = true,
                backward = false,
                left = false,
                right = false,
                jump = false,
                shift = false,
                sprint = false
            )
        )
        PlayerTestUtil.sendPacket(
            player,
            ServerboundClientInputPacket(
                forward = false,
                backward = false,
                left = false,
                right = false,
                jump = false,
                shift = false,
                sprint = false
            )
        )

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}