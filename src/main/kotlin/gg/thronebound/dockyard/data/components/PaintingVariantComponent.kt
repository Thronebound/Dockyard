package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.registry.registries.PaintingVariant
import gg.thronebound.dockyard.registry.registries.PaintingVariantRegistry
import io.netty.buffer.ByteBuf

data class PaintingVariantComponent(val variant: PaintingVariant) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeVarInt(variant.getProtocolId())
    }

    companion object : NetworkReadable<PaintingVariantComponent> {
        override fun read(buffer: ByteBuf): PaintingVariantComponent {
            return PaintingVariantComponent(buffer.readVarInt().let { int -> PaintingVariantRegistry.getByProtocolId(int) })
        }
    }
}