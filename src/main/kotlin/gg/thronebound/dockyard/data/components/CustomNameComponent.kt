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

class CustomNameComponent(val component: Component) : DataComponent() {

    constructor(name: String) : this(name.toComponent())

    override fun write(buffer: ByteBuf) {
        buffer.writeTextComponent(component)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofNbt(component.toNBT()))
    }

    companion object : NetworkReadable<CustomNameComponent> {
        override fun read(buffer: ByteBuf): CustomNameComponent {
            return CustomNameComponent(buffer.readTextComponent())
        }
    }
}