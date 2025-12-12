package gg.thronebound.dockyard.protocol.encoders

import cz.lukynka.prettylog.LogType
import cz.lukynka.prettylog.log
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class RawPacketEncoder: MessageToByteEncoder<ClientboundPacket>() {

    override fun encode(connection: ChannelHandlerContext, packet: ClientboundPacket, out: ByteBuf) {
        try {
            out.writeVarInt(packet.id!!)
            out.writeBytes(packet.buffer.copy())
        } catch (exception: Exception) {
            log("There was an error while encoding packet", LogType.ERROR)
            log(exception)
        }
    }
}