package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.events.ClientTickEndEvent
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.utils.getPlayerEventContext
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundClientTickEndPacket: ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        Events.dispatch(ClientTickEndEvent(processor.player, getPlayerEventContext(processor.player)))
    }

    companion object {
        fun read(buffer: ByteBuf): ServerboundClientTickEndPacket {
            return ServerboundClientTickEndPacket()
        }
    }
}