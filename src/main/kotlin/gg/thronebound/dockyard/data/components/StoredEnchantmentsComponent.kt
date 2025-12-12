package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.item.Enchantment
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.readList
import gg.thronebound.dockyard.protocol.types.writeList
import io.netty.buffer.ByteBuf

class StoredEnchantmentsComponent(val enchantments: List<Enchantment>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeList(enchantments, Enchantment::write)
    }

    override fun hashStruct(): HashHolder {
        return unsupported(this)
    }

    companion object : NetworkReadable<StoredEnchantmentsComponent> {
        override fun read(buffer: ByteBuf): StoredEnchantmentsComponent {
            return StoredEnchantmentsComponent(buffer.readList(Enchantment::read))
        }
    }

}