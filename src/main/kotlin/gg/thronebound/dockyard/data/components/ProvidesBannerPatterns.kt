package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

class ProvidesBannerPatterns(val identifier: String) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeString(identifier)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofString(identifier))
    }

    companion object : NetworkReadable<ProvidesBannerPatterns> {
        override fun read(buffer: ByteBuf): ProvidesBannerPatterns {
            return ProvidesBannerPatterns(buffer.readString())
        }
    }
}