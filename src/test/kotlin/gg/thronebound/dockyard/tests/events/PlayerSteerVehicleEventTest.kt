package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.entity.EntityManager
import gg.thronebound.dockyard.entity.Squid
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerSteerVehicleEvent
import gg.thronebound.dockyard.location.Point
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundMoveVehiclePacket
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerSteerVehicleEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        val vehicle = EntityManager.spawnEntity(Squid(player.location))
        vehicle.passengers.add(player)

        pool.on<PlayerSteerVehicleEvent> {
            if (it.vehicle == vehicle && it.player == player) {
                count.countDown()
            }
        }

        PlayerTestUtil.sendPacket(ServerboundMoveVehiclePacket(Point(0.0, 0.0, 0.0, 0f, 0f), true))

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()

        vehicle.dispose()
    }
}