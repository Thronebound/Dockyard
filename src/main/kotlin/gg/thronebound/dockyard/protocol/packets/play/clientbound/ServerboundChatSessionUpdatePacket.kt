package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.readByteArray
import gg.thronebound.dockyard.extentions.readInstant
import gg.thronebound.dockyard.extentions.readUUID
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.cryptography.PlayerSession
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundChatSessionUpdatePacket(
    val playerSession: PlayerSession
) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        // dockyard does not support encrypted chat
    }

    companion object {
        fun read(buffer: ByteBuf): ServerboundChatSessionUpdatePacket {
            return ServerboundChatSessionUpdatePacket(PlayerSession(
                buffer.readUUID(),
                buffer.readInstant(),
                buffer.readByteArray(),
                buffer.readByteArray()
            ))
        }
    }
}
