package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

class RabbitVariantComponent(val variant: Variant) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeEnum(variant)
    }

    companion object : NetworkReadable<RabbitVariantComponent> {
        override fun read(buffer: ByteBuf): RabbitVariantComponent {
            return RabbitVariantComponent(buffer.readEnum())
        }
    }

    enum class Variant {
        BROWN,
        WHITE,
        BLACK,
        BLACK_AND_WHITE,
        GOLD,
        SALT_AND_PEPPER,
        KILLER_BUNNY
    }
}