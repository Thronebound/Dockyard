package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.DyeColor
import io.netty.buffer.ByteBuf

data class TropicalFishBaseColorComponent(val color: DyeColor) : DataComponent() {


    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofEnum(color))
    }


    override fun write(buffer: ByteBuf) {
        buffer.writeEnum(color)
    }

    companion object : NetworkReadable<TropicalFishBaseColorComponent> {
        override fun read(buffer: ByteBuf): TropicalFishBaseColorComponent {
            return TropicalFishBaseColorComponent(buffer.readEnum())
        }
    }
}