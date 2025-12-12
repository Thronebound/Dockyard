package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.utils.debug
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundPlayPingResponsePacket(val number: Long): ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
//        processor.player.ping = number
//        processor.player.sendPacket(ClientboundPlayPingResponsePacket(number))
    }

    companion object {
        fun read(buffer: ByteBuf): ServerboundPlayPingResponsePacket {
            return ServerboundPlayPingResponsePacket(buffer.readLong())
        }
    }
}