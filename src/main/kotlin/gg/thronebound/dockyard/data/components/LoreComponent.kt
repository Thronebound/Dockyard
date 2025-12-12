package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readList
import gg.thronebound.dockyard.extentions.readTextComponent
import gg.thronebound.dockyard.extentions.writeTextComponent
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.writeList
import io.github.dockyardmc.scroll.Component
import io.netty.buffer.ByteBuf

data class LoreComponent(val lore: List<Component>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeList(lore, ByteBuf::writeTextComponent)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofList(lore.map { component -> CRC32CHasher.ofNbt(component.toNBT()) }))
    }

    companion object : NetworkReadable<LoreComponent> {
        override fun read(buffer: ByteBuf): LoreComponent {
            return LoreComponent(buffer.readList(ByteBuf::readTextComponent))
        }
    }
}