package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.HorseJumpEvent
import gg.thronebound.dockyard.player.PlayerAction
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundPlayerCommandPacket
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class HorseJumpEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<HorseJumpEvent> {
            count.countDown()
        }
        val player = PlayerTestUtil.getOrCreateFakePlayer()

        PlayerTestUtil.sendPacket(
            player,
            ServerboundPlayerCommandPacket(player.id, PlayerAction.HORSE_JUMP_START)
        )

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}