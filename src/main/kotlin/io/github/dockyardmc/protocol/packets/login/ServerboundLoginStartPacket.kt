package io.github.dockyardmc.protocol.packets.login

import io.github.dockyardmc.annotations.ServerboundPacketInfo
import io.github.dockyardmc.annotations.WikiVGEntry
import io.github.dockyardmc.extentions.readUUID
import io.github.dockyardmc.extentions.readUtf
import io.github.dockyardmc.protocol.PacketProcessor
import io.github.dockyardmc.protocol.packets.ProtocolState
import io.github.dockyardmc.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import java.util.UUID

@WikiVGEntry("Login Start")
@ServerboundPacketInfo(0x00, ProtocolState.LOGIN)
class ServerboundLoginStartPacket(val name: String, val uuid: UUID): ServerboundPacket {

    override fun handle(processor: PacketProcessor, connection: ChannelHandlerContext, size: Int, id: Int) {
        processor.loginHandler.handleLoginStart(this, connection)
    }

    companion object {
        fun read(byteBuf: ByteBuf): ServerboundLoginStartPacket {

            val name = byteBuf.readUtf(16)
            val uuid = byteBuf.readUUID()
            return ServerboundLoginStartPacket(name, uuid)
        }
    }
}