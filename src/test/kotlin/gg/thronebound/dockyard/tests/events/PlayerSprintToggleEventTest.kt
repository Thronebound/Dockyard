package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerSprintToggleEvent
import gg.thronebound.dockyard.player.PlayerAction
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundPlayerCommandPacket
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerSprintToggleEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(2)

        pool.on<PlayerSprintToggleEvent> {
            count.countDown()
        }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        PlayerTestUtil.sendPacket(player, ServerboundPlayerCommandPacket(player.id, PlayerAction.SPRINTING_START))
        PlayerTestUtil.sendPacket(player, ServerboundPlayerCommandPacket(player.id, PlayerAction.SPRINTING_END))

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}