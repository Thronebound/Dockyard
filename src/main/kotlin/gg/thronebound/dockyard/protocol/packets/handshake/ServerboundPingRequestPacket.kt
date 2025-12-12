package gg.thronebound.dockyard.protocol.packets.handshake

import gg.thronebound.dockyard.extentions.sendPacket
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import java.time.Instant

class ServerboundPingRequestPacket(val time: Long) : ServerboundPacket {

    companion object {
        fun read(buffer: ByteBuf): ServerboundPingRequestPacket {
            return ServerboundPingRequestPacket(buffer.readLong())
        }
    }

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val out = ClientboundPingResponsePacket(time)
        connection.sendPacket(out, processor)
    }
}