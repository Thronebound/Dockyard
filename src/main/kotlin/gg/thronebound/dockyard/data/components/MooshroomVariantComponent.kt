package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

class MooshroomVariantComponent(val variant: Variant) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeEnum(variant)
    }

    companion object : NetworkReadable<MooshroomVariantComponent> {
        override fun read(buffer: ByteBuf): MooshroomVariantComponent {
            return MooshroomVariantComponent(buffer.readEnum())
        }
    }

    enum class Variant {
        RED,
        BROWN
    }
}