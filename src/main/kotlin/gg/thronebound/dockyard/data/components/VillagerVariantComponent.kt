package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

data class VillagerVariantComponent(val type: Type) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeEnum(type)
    }

    companion object : NetworkReadable<VillagerVariantComponent> {
        override fun read(buffer: ByteBuf): VillagerVariantComponent {
            return VillagerVariantComponent(buffer.readEnum())
        }
    }

    enum class Type(val identifier: String) {
        DESERT("minecraft:desert"),
        JUNGLE("minecraft:jungle"),
        PLAINS("minecraft:plains"),
        SAVANNA("minecraft:savanna"),
        SNOW("minecraft:snow"),
        SWAMP("minecraft:swamp"),
        TAIGA("minecraft:taiga")
    }
}