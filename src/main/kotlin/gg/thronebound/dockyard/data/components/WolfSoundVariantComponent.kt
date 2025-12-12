package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.registry.registries.WolfSoundVariant
import gg.thronebound.dockyard.registry.registries.WolfSoundVariantRegistry
import io.netty.buffer.ByteBuf

class WolfSoundVariantComponent(val variant: WolfSoundVariant) : DynamicVariantComponent<WolfSoundVariant>(variant, WolfSoundVariantRegistry) {

    companion object : NetworkReadable<WolfSoundVariantComponent> {
        override fun read(buffer: ByteBuf): WolfSoundVariantComponent {
            return WolfSoundVariantComponent(buffer.readVarInt().let { int -> WolfSoundVariantRegistry.getByProtocolId(int) })
        }
    }
}