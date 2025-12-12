package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.*
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

data class AxolotlVariantComponent(val variant: Variant) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeEnum(variant)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofEnum(variant))
    }

    companion object : NetworkReadable<AxolotlVariantComponent> {
        override fun read(buffer: ByteBuf): AxolotlVariantComponent {
            return AxolotlVariantComponent(buffer.readEnum())
        }
    }

    enum class Variant {
        LUCY,
        WILD,
        GOLD,
        CYAN,
        BLUE // <-- rare shiny one :3
    }
}