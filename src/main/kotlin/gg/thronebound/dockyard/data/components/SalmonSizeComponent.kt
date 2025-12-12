package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

data class SalmonSizeComponent(val size: Size) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeEnum(size)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofEnum(size))
    }

    companion object : NetworkReadable<SalmonSizeComponent> {
        override fun read(buffer: ByteBuf): SalmonSizeComponent {
            return SalmonSizeComponent(buffer.readEnum())
        }
    }

    enum class Size {
        SMALL,
        MEDIUM,
        LARGE
    }
}