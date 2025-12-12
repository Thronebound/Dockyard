package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerSwapOffhandEvent
import gg.thronebound.dockyard.inventory.clearInventory
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.protocol.packets.play.serverbound.PlayerAction
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundPlayerActionPacket
import gg.thronebound.dockyard.registry.Items
import gg.thronebound.dockyard.math.vectors.Vector3
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerSwapOffhandEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.setHeldItem(PlayerHand.MAIN_HAND, Items.DIAMOND_SWORD.toItemStack(5))

        pool.on<PlayerSwapOffhandEvent> {
            if (it.mainHandItem.material == Items.DIAMOND_SWORD) {
                count.countDown()
            }
        }

        PlayerTestUtil.sendPacket(player, ServerboundPlayerActionPacket(PlayerAction.SWAP_ITEM, Vector3(0), Direction.DOWN, 0))

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()

        player.clearInventory()
    }
}