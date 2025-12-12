package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.registry.registries.PigVariant
import gg.thronebound.dockyard.registry.registries.PigVariantRegistry
import io.netty.buffer.ByteBuf

class PigVariantComponent(val variant: PigVariant) : DynamicVariantComponent<PigVariant>(variant, PigVariantRegistry) {

    companion object : NetworkReadable<PigVariantComponent> {
        override fun read(buffer: ByteBuf): PigVariantComponent {
            return PigVariantComponent(buffer.readVarInt().let { int -> PigVariantRegistry.getByProtocolId(int) })
        }
    }
}