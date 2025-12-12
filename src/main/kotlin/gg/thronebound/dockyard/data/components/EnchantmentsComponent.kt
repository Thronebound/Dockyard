package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.item.Enchantment
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.readList
import gg.thronebound.dockyard.protocol.types.writeList
import io.netty.buffer.ByteBuf

class EnchantmentsComponent(val list: List<Enchantment>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeList(list, Enchantment::write)
    }

    //TODO(Enchantments)
    override fun hashStruct(): HashHolder {
        return unsupported(this::class)
    }

    companion object : NetworkReadable<EnchantmentsComponent> {
        override fun read(buffer: ByteBuf): EnchantmentsComponent {
            return EnchantmentsComponent(buffer.readList(Enchantment::read))
        }
    }
}