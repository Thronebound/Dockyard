package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.registry.Registry
import gg.thronebound.dockyard.registry.RegistryEntry
import io.netty.buffer.ByteBuf

abstract class DynamicVariantComponent<T : RegistryEntry>(internal val entry: T, val registry: Registry<*>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeVarInt(entry.getProtocolId())
    }

}