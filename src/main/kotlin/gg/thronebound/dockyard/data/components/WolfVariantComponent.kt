package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.registry.registries.WolfVariant
import gg.thronebound.dockyard.registry.registries.WolfVariantRegistry
import io.netty.buffer.ByteBuf

class WolfVariantComponent(val variant: WolfVariant) : DynamicVariantComponent<WolfVariant>(variant, WolfVariantRegistry) {

    companion object : NetworkReadable<WolfVariantComponent> {
        override fun read(buffer: ByteBuf): WolfVariantComponent {
            return WolfVariantComponent(buffer.readVarInt().let { int -> WolfVariantRegistry.getByProtocolId(int) })
        }
    }
}