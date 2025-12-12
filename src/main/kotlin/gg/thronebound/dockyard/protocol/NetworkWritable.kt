package gg.thronebound.dockyard.protocol

import io.netty.buffer.ByteBuf

interface NetworkWritable {
    fun write(buffer: ByteBuf)
}

