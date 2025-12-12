package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

class FoxVariantComponent(val variant: Variant) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeEnum(variant)
    }

    companion object : NetworkReadable<FoxVariantComponent> {
        override fun read(buffer: ByteBuf): FoxVariantComponent {
            return FoxVariantComponent(buffer.readEnum())
        }
    }

    enum class Variant {
        RED,
        SNOW
    }
}