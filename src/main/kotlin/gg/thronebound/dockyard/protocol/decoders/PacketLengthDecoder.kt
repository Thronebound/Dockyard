package gg.thronebound.dockyard.protocol.decoders

import cz.lukynka.prettylog.LogType
import cz.lukynka.prettylog.log
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.server.ServerMetrics
import gg.thronebound.dockyard.utils.DataSizeCounter
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class PacketLengthDecoder : ByteToMessageDecoder() {

    override fun decode(connection: ChannelHandlerContext, buffer: ByteBuf, out: MutableList<Any>) {
        if (!connection.channel().isActive) return

        buffer.markReaderIndex()
        val length = buffer.readVarInt()

        // reset the reader index if we don't have enough bytes and wait for next part of the message to arrive and check again
        if (length > buffer.readableBytes()) {
            buffer.resetReaderIndex()
            return
        }

        out.add(buffer.retainedSlice(buffer.readerIndex(), length))
        buffer.skipBytes(length)
        // +1 to account for the size byte that is not counted
        ServerMetrics.inboundBandwidth.add(length + 1, DataSizeCounter.Type.BYTE)
        ServerMetrics.totalBandwidth.add(length + 1, DataSizeCounter.Type.BYTE)
    }

    override fun exceptionCaught(connection: ChannelHandlerContext, cause: Throwable) {
        connection.channel().close().sync()
        if (cause.message == "An established connection was aborted by the software in your host machine") return

        log("Error occurred while decoding frame: ", LogType.ERROR)
        val exception = (if (cause.cause == null) cause else cause.cause) as Exception
        log(exception)
    }
}