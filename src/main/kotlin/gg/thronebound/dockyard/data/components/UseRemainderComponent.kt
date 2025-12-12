package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

class UseRemainderComponent(val remained: ItemStack) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        remained.write(buffer)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(remained.hashStruct().getHashed())
    }

    companion object : NetworkReadable<UseRemainderComponent> {
        override fun read(buffer: ByteBuf): UseRemainderComponent {
            return UseRemainderComponent(ItemStack.read(buffer))
        }
    }
}