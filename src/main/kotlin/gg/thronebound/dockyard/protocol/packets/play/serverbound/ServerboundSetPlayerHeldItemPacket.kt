package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PlayerSelectedHotbarSlotChangeEvent
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.utils.getPlayerEventContext
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundSetPlayerHeldItemPacket(val slot: Int) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val player = processor.player
        val beforeSlot = player.heldSlotIndex.value

        val event = PlayerSelectedHotbarSlotChangeEvent(processor.player, slot, getPlayerEventContext(player))
        Events.dispatch(event)

        if (event.cancelled) {
            player.heldSlotIndex.value = beforeSlot
            return
        }
        player.heldSlotIndex.value = slot
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundSetPlayerHeldItemPacket =
            ServerboundSetPlayerHeldItemPacket(buf.readShort().toInt())
    }
}