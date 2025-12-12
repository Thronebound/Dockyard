package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readRegistryEntry
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.registry.registries.ChickenVariant
import gg.thronebound.dockyard.registry.registries.ChickenVariantRegistry
import io.netty.buffer.ByteBuf

class ChickenVariantComponent(val variant: ChickenVariant) : DynamicVariantComponent<ChickenVariant>(variant, ChickenVariantRegistry) {

    companion object : NetworkReadable<ChickenVariantComponent> {
        override fun read(buffer: ByteBuf): ChickenVariantComponent {
            return ChickenVariantComponent(buffer.readRegistryEntry(ChickenVariantRegistry))
        }
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofRegistryEntry(variant))
    }
}