package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.entity.ParrotVariant
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

data class ParrotVariantComponent(val parrotColor: ParrotVariant) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeEnum(parrotColor)
    }

    companion object : NetworkReadable<ParrotVariantComponent> {
        override fun read(buffer: ByteBuf): ParrotVariantComponent {
            return ParrotVariantComponent(buffer.readEnum())
        }
    }
}