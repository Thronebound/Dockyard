package gg.thronebound.dockyard.tests.block.handlers

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PlayerFinishPlacingBlockEvent
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundUseItemOnBlockPacket
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.registry.registries.Item
import gg.thronebound.dockyard.math.vectors.Vector3
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.world.WorldManager
import gg.thronebound.dockyard.world.block.Block
import java.util.concurrent.CountDownLatch

object BlockHandlerTestUtil {

    fun reset() {
        WorldManager.mainWorld.setBlock(0, 0, 0, Blocks.AIR)
    }

    fun place(item: Item, face: Direction, clickLocation: Vector3, cursor: Vector3f): Block {
        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.setHeldItem(PlayerHand.MAIN_HAND, item.toItemStack(1))

        val latch = CountDownLatch(1)
        val packet = ServerboundUseItemOnBlockPacket(PlayerHand.MAIN_HAND, clickLocation, face, cursor.x, cursor.y, cursor.z, false, hitWorldBorder = false, sequence = 0)

        val listener = Events.on<PlayerFinishPlacingBlockEvent> { event ->
            latch.countDown()
        }

        PlayerTestUtil.sendPacket(PlayerTestUtil.getOrCreateFakePlayer(), packet)
        latch.await()
        Events.unregister(listener)
        return WorldManager.mainWorld.getBlock(0, 0, 0)
    }
}