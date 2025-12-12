package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.registry.registries.CowVariant
import gg.thronebound.dockyard.registry.registries.CowVariantRegistry
import io.netty.buffer.ByteBuf

class CowVariantComponent(val variant: CowVariant) : DynamicVariantComponent<CowVariant>(variant, CowVariantRegistry) {

    companion object : NetworkReadable<CowVariantComponent> {
        override fun read(buffer: ByteBuf): CowVariantComponent {
            return CowVariantComponent(buffer.readVarInt().let { int -> CowVariantRegistry.getByProtocolId(int) })
        }
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofRegistryEntry(variant))
    }
}