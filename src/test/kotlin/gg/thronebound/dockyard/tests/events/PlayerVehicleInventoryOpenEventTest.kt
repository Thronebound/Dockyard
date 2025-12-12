package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerVehicleInventoryOpenEvent
import gg.thronebound.dockyard.player.PlayerAction
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundPlayerCommandPacket
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerVehicleInventoryOpenEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        val player = PlayerTestUtil.getOrCreateFakePlayer()

        pool.on<PlayerVehicleInventoryOpenEvent> {
            count.countDown()
        }

        PlayerTestUtil.sendPacket(player, ServerboundPlayerCommandPacket(player.id, PlayerAction.VEHICLE_INVENTORY_OPEN))

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}