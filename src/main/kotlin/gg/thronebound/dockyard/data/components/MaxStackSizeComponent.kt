package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.github.dockyardmc.tide.stream.StreamCodec
import io.netty.buffer.ByteBuf

data class MaxStackSizeComponent(val size: Int) : DataComponent(true) {

    override fun write(buffer: ByteBuf) {
        STREAM_CODEC.write(buffer, this)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofInt(size))
    }

    companion object : NetworkReadable<MaxStackSizeComponent> {
        val STREAM_CODEC = StreamCodec.of(
            StreamCodec.VAR_INT, MaxStackSizeComponent::size,
            ::MaxStackSizeComponent
        )

        override fun read(buffer: ByteBuf): MaxStackSizeComponent {
            return STREAM_CODEC.read(buffer)
        }
    }
}