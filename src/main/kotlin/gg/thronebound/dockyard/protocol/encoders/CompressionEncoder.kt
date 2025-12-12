package gg.thronebound.dockyard.protocol.encoders

import cz.lukynka.prettylog.LogType
import cz.lukynka.prettylog.log
import gg.thronebound.dockyard.extentions.readRemainingBytesAsByteArray
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.NetworkCompression
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class CompressionEncoder(val processor: PlayerNetworkManager) : MessageToByteEncoder<ByteBuf>() {

    override fun encode(connection: ChannelHandlerContext, buffer: ByteBuf, out: ByteBuf) {
        try {
            val dataLength = buffer.readableBytes()
            if (dataLength < NetworkCompression.COMPRESSION_THRESHOLD) {
                out.writeVarInt(0)
                out.writeBytes(buffer)
            } else {
                out.writeVarInt(dataLength)
                out.writeBytes(NetworkCompression.compress(buffer.readRemainingBytesAsByteArray()))
            }
        } catch (exception: Exception) {
            log("There was an error while compressing packet", LogType.ERROR)
            log(exception)
        }
    }
}