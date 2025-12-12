package gg.thronebound.dockyard.protocol

import io.netty.buffer.ByteBuf

interface NetworkReadable <T> {
    fun read(buffer: ByteBuf): T
}