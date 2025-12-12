package gg.thronebound.dockyard.protocol.packets.login

import gg.thronebound.dockyard.extentions.readByteArray
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundEncryptionResponsePacket(var sharedSecret: ByteArray, var verifyToken: ByteArray) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        processor.loginHandler.handleEncryptionResponse(this, connection)
    }

    companion object {
        fun read(byteBuf: ByteBuf): ServerboundEncryptionResponsePacket {
            val packet = ServerboundEncryptionResponsePacket(byteBuf.readByteArray().clone(), byteBuf.readByteArray().clone())
            return packet
        }
    }
}