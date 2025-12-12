package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

class MapPostProcessing(val type: Type) : DataComponent() {

    enum class Type {
        LOCK,
        SCALE
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofEnum(type))
    }

    override fun write(buffer: ByteBuf) {
        buffer.writeEnum(type)
    }

    companion object : NetworkReadable<MapPostProcessing> {
        override fun read(buffer: ByteBuf): MapPostProcessing {
            return MapPostProcessing(buffer.readEnum())
        }
    }
}