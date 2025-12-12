package gg.thronebound.dockyard.protocol.packets.login

import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.protocol.readOptional
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundLoginPluginResponsePacket(val messageId: Int, val data: ByteBuf? = null) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        processor.loginPluginMessageHandler.handleResponse(messageId, data)
    }

    companion object : NetworkReadable<ServerboundLoginPluginResponsePacket> {
        override fun read(buffer: ByteBuf): ServerboundLoginPluginResponsePacket {
            return ServerboundLoginPluginResponsePacket(buffer.readVarInt(), buffer.readOptional { b -> b.readBytes(b.readableBytes()) })
        }
    }
}