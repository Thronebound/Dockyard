package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readTextComponent
import gg.thronebound.dockyard.extentions.writeTextComponent
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.github.dockyardmc.scroll.Component
import io.github.dockyardmc.scroll.extensions.toComponent
import io.netty.buffer.ByteBuf

class ItemNameComponent(val itemName: Component) : DataComponent() {

    constructor(itemName: String): this(itemName.toComponent())

    override fun write(buffer: ByteBuf) {
        buffer.writeTextComponent(itemName)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofNbt(itemName.toNBT()))
    }

    companion object : NetworkReadable<ItemNameComponent> {
        override fun read(buffer: ByteBuf): ItemNameComponent {
            return ItemNameComponent(buffer.readTextComponent())
        }
    }
}