package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestFor
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerBlockPlaceEvent
import gg.thronebound.dockyard.events.PlayerFinishPlacingBlockEvent
import gg.thronebound.dockyard.inventory.clearInventory
import gg.thronebound.dockyard.math.vectors.Vector3
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.player.systems.GameMode
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundUseItemOnBlockPacket
import gg.thronebound.dockyard.registry.Items
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@TestFor(PlayerBlockPlaceEvent::class, PlayerFinishPlacingBlockEvent::class)
class PlayerBlockPlaceEventTest {

    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val placeCount = CountDownLatch(1)
        val finishCount = CountDownLatch(1)

        pool.on<PlayerBlockPlaceEvent> {
            placeCount.countDown()
        }
        pool.on<PlayerFinishPlacingBlockEvent> {
            finishCount.countDown()
        }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.gameMode.value = GameMode.SURVIVAL
        player.setHeldItem(PlayerHand.MAIN_HAND, Items.DIRT.toItemStack(9))

        PlayerTestUtil.sendPacket(
            player,
            ServerboundUseItemOnBlockPacket(
                PlayerHand.MAIN_HAND,
                Vector3(0),
                Direction.UP,
                0f, 0f, 0f,
                insideBlock = false, hitWorldBorder = false, sequence = 0
            )
        )

        assertTrue(placeCount.await(5L, TimeUnit.SECONDS))
        assertTrue(finishCount.await(5L, TimeUnit.SECONDS))

        pool.dispose()
        player.clearInventory()
    }
}