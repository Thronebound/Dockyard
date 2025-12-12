package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PlayerFlightToggleEvent
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.utils.getPlayerEventContext
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundPlayerAbilitiesPacket(val flying: Boolean) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val player = processor.player
        processor.player.isFlying.setSilently(flying)
        val event = PlayerFlightToggleEvent(player, flying, getPlayerEventContext(player))
        Events.dispatch(event)
        if (event.cancelled) {
            player.isFlying.value = false
        }
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundPlayerAbilitiesPacket {
            val byte = buf.readByte()
            val flying = byte.toInt() == 2
            return ServerboundPlayerAbilitiesPacket(flying)
        }
    }
}