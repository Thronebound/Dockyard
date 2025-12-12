package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerFlightToggleEvent
import gg.thronebound.dockyard.player.systems.GameMode
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundPlayerAbilitiesPacket
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerFlightToggleEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testToggleFlight() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.gameMode.value = GameMode.CREATIVE
        player.isFlying.value = false

        pool.on<PlayerFlightToggleEvent> {
            if (it.flying) {
                count.countDown()
            }
        }

        PlayerTestUtil.sendPacket(player, ServerboundPlayerAbilitiesPacket(true))

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()

        player.gameMode.value = GameMode.SURVIVAL
    }
}