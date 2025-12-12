package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

class TooltipStyleComponent(val style: String) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeString(style)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofString(style))
    }

    companion object : NetworkReadable<TooltipStyleComponent> {
        override fun read(buffer: ByteBuf): TooltipStyleComponent {
            return TooltipStyleComponent(buffer.readString())
        }
    }

}