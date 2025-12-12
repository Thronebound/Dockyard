package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.asRGB
import gg.thronebound.dockyard.extentions.fromRGBInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.github.dockyardmc.scroll.CustomColor
import io.netty.buffer.ByteBuf

class DyedColorComponent(val color: CustomColor) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeInt(color.asRGB())
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofColor(color))
    }

    companion object : NetworkReadable<DyedColorComponent> {
        override fun read(buffer: ByteBuf): DyedColorComponent {
            return DyedColorComponent(CustomColor.fromRGBInt(buffer.readInt()))
        }
    }

}