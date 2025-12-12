package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerMoveEvent
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundSetPlayerPositionPacket
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerMoveEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<PlayerMoveEvent> {
            count.countDown()
        }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        val vector = player.location.add(1,0,0).toVector3d()

        PlayerTestUtil.sendPacket(player, ServerboundSetPlayerPositionPacket(vector, isOnGround = true, horizontalCollision = false))

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}