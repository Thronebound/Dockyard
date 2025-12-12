package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.readList
import gg.thronebound.dockyard.protocol.types.writeList
import io.netty.buffer.ByteBuf

data class ContainerComponent(val items: List<ItemStack>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeList(items, ItemStack::write)
    }

    override fun hashStruct(): HashHolder {
        val finalHash = mutableListOf<Int>()
        items.forEach { item ->
            finalHash.add(item.hashStruct().getHashed())
        }
        return StaticHash(CRC32CHasher.ofList(finalHash))

    }

    companion object : NetworkReadable<ContainerComponent> {
        override fun read(buffer: ByteBuf): ContainerComponent {
            return ContainerComponent(buffer.readList(ItemStack::read))
        }
    }
}