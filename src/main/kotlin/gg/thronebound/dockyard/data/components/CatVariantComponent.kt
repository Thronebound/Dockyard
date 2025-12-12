package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readRegistryEntry
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.registry.registries.CatVariant
import gg.thronebound.dockyard.registry.registries.CatVariantRegistry
import io.netty.buffer.ByteBuf

data class CatVariantComponent(val variant: CatVariant) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeVarInt(variant.getProtocolId())
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofRegistryEntry(variant))
    }

    companion object : NetworkReadable<CatVariantComponent> {
        override fun read(buffer: ByteBuf): CatVariantComponent {
            return CatVariantComponent(buffer.readRegistryEntry(CatVariantRegistry))
        }
    }
}