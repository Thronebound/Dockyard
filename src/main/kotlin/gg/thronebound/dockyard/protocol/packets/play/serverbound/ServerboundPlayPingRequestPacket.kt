package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.extentions.sendPacket
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundPlayPingResponsePacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundPlayPingRequestPacket(val payload: Long) : ServerboundPacket {
    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        connection.sendPacket(ClientboundPlayPingResponsePacket(payload), processor)
    }

    companion object : NetworkReadable<ServerboundPlayPingRequestPacket> {
        override fun read(buffer: ByteBuf): ServerboundPlayPingRequestPacket {
            return ServerboundPlayPingRequestPacket(
                buffer.readLong()
            )
        }
    }
}
