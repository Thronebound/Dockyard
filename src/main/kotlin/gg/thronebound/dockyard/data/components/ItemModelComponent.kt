package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

class ItemModelComponent(val itemModel: String) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeString(itemModel)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofString(itemModel))
    }

    companion object : NetworkReadable<ItemModelComponent> {
        override fun read(buffer: ByteBuf): ItemModelComponent {
            return ItemModelComponent(buffer.readString())
        }
    }
}