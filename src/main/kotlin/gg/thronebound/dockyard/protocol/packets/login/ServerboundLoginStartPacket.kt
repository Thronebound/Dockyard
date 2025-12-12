package gg.thronebound.dockyard.protocol.packets.login

import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.readUUID
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import java.util.*

class ServerboundLoginStartPacket(val name: String, val uuid: UUID) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        processor.loginHandler.handleLoginStart(this, connection)
    }

    companion object {
        fun read(byteBuf: ByteBuf): ServerboundLoginStartPacket {

            val name = byteBuf.readString(16)
            val uuid = byteBuf.readUUID()
            return ServerboundLoginStartPacket(name, uuid)
        }
    }
}