package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PlayerPickItemFromBlockEvent
import gg.thronebound.dockyard.location.readBlockPosition
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.player.systems.GameMode
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.utils.getPlayerEventContext
import gg.thronebound.dockyard.math.vectors.Vector3
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundPickItemFromBlockPacket(val blockPosition: Vector3, val includeData: Boolean): ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val player = processor.player

        val location = blockPosition.toLocation(player.world)
        val block = location.block

        val event = PlayerPickItemFromBlockEvent(processor.player, location, block, includeData, getPlayerEventContext(player))
        Events.dispatch(event)
        if(event.cancelled) return
        if(event.block.isAir()) return

        if(player.gameMode.value == GameMode.CREATIVE) {
            player.mainHandItem = event.block.toItem().toItemStack()
        } else {
            val slot = player.inventory.getSlotByItem(block.toItem()) ?: return

            if(slot <= 8) {
                player.heldSlotIndex.value = slot

            } else {
                val current = player.getHeldItem(PlayerHand.MAIN_HAND)
                val item = player.inventory[slot]

                player.setHeldItem(PlayerHand.MAIN_HAND, item)
                player.inventory[slot] = current
            }
        }
    }

    companion object {
        fun read(buffer: ByteBuf): ServerboundPickItemFromBlockPacket {
            return ServerboundPickItemFromBlockPacket(buffer.readBlockPosition(), buffer.readBoolean())
        }
    }
}