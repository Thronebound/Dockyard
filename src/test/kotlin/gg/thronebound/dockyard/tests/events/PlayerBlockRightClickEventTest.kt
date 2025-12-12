package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerBlockRightClickEvent
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundUseItemOnBlockPacket
import gg.thronebound.dockyard.registry.Items
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerBlockRightClickEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<PlayerBlockRightClickEvent> {
            count.countDown()
        }
        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.mainHandItem = Items.APPLE.toItemStack()

        PlayerTestUtil.sendPacket(
            player,
            ServerboundUseItemOnBlockPacket(
                PlayerHand.MAIN_HAND,
                player.location.toVector3(),
                Direction.UP,
                cursorX = 0f,
                cursorY = 0f,
                cursorZ = 0f,
                insideBlock = false,
                hitWorldBorder = false,
                sequence = 0, 
            )
        )

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
        player.inventory.clear()
    }
}
