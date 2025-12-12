package gg.thronebound.dockyard.protocol.encoders

import cz.lukynka.prettylog.LogType
import cz.lukynka.prettylog.log
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.server.ServerMetrics
import gg.thronebound.dockyard.utils.DataSizeCounter
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class PacketLengthEncoder: MessageToByteEncoder<ByteBuf>() {

    override fun encode(connection: ChannelHandlerContext, buffer: ByteBuf, out: ByteBuf) {
        try {
            val size = buffer.readableBytes()
            out.writeVarInt(size)
            out.writeBytes(buffer)

            // +1 to account for the size byte that is not counted
            ServerMetrics.outboundBandwidth.add(size + 1, DataSizeCounter.Type.BYTE)
            ServerMetrics.totalBandwidth.add(size + 1, DataSizeCounter.Type.BYTE)
        } catch (exception: Exception) {
            log("There was an error while encoding packet length", LogType.ERROR)
            log(exception)
        }
    }
}