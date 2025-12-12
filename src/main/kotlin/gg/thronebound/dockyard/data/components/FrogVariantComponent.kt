package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.registry.registries.FrogVariant
import gg.thronebound.dockyard.registry.registries.FrogVariantRegistry
import io.netty.buffer.ByteBuf

class FrogVariantComponent(val variant: FrogVariant) : DynamicVariantComponent<FrogVariant>(variant, FrogVariantRegistry) {

    companion object : NetworkReadable<FrogVariantComponent> {
        override fun read(buffer: ByteBuf): FrogVariantComponent {
            return FrogVariantComponent(buffer.readVarInt().let { int -> FrogVariantRegistry.getByProtocolId(int) })
        }
    }
}