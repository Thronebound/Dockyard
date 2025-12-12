package gg.thronebound.dockyard.protocol.packets.login

import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundLoginAcknowledgedPacket : ServerboundPacket {
    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        processor.loginHandler.handleLoginAcknowledge(this, connection)
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundLoginAcknowledgedPacket = ServerboundLoginAcknowledgedPacket()
    }
}