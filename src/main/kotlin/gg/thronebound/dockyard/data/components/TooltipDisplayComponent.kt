package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.DataComponentRegistry
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.extentions.getOrThrow
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.readList
import gg.thronebound.dockyard.protocol.types.writeList
import io.netty.buffer.ByteBuf
import kotlin.reflect.KClass

class TooltipDisplayComponent(val hideTooltip: Boolean, val hiddenComponents: List<KClass<out DataComponent>>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeBoolean(hideTooltip)
        buffer.writeList(hiddenComponents.map { component -> DataComponentRegistry.dataComponentsByIdReversed.getValue(component) }, ByteBuf::writeVarInt)
    }

    override fun hashStruct(): HashHolder {
        return CRC32CHasher.of {
            default("hide_tooltip", false, hideTooltip, CRC32CHasher::ofBoolean)
            defaultList("hidden_components", listOf(), hiddenComponents.map { component -> DataComponentRegistry.dataComponentsByIdentifierReversed.getOrThrow(component) }, CRC32CHasher::ofString)
        }
    }

    companion object : NetworkReadable<TooltipDisplayComponent> {
        override fun read(buffer: ByteBuf): TooltipDisplayComponent {
            return TooltipDisplayComponent(
                buffer.readBoolean(),
                buffer.readList(ByteBuf::readVarInt).map { int -> DataComponentRegistry.dataComponentsById.getValue(int) }
            )
        }
    }
}