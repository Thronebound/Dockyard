package gg.thronebound.dockyard.protocol.packets.login

import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import io.netty.buffer.ByteBuf

data class ClientboundLoginPluginRequestPacket(val messageId: Int, val channel: String, val data: ByteBuf) : ClientboundPacket() {

    init {
        buffer.writeVarInt(messageId)
        buffer.writeString(channel)
        buffer.writeBytes(data)
    }
}