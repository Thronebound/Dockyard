package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.DyeColor
import io.netty.buffer.ByteBuf

data class WolfCollarComponent(val color: DyeColor) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeEnum(color)
    }

    companion object : NetworkReadable<WolfCollarComponent> {
        override fun read(buffer: ByteBuf): WolfCollarComponent {
            return WolfCollarComponent(buffer.readEnum())
        }
    }
}