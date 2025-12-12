package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerRightClickWithItemEvent
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundUseItemPacket
import gg.thronebound.dockyard.registry.Items
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerRightClickWithItemEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<PlayerRightClickWithItemEvent> { count.countDown() }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.setHeldItem(PlayerHand.MAIN_HAND, Items.DEBUG_STICK.toItemStack())

        PlayerTestUtil.sendPacket(
            player, ServerboundUseItemPacket(PlayerHand.MAIN_HAND, 0, 0f, 0f)
        )

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}