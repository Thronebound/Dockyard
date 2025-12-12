package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.attributes.Modifier
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.HashList
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.readList
import gg.thronebound.dockyard.protocol.types.writeList
import io.netty.buffer.ByteBuf

class AttributeModifiersComponent(val attributes: List<Modifier>) : DataComponent(true) {

    override fun write(buffer: ByteBuf) {
        buffer.writeList(attributes, Modifier::write)
    }

    override fun hashStruct(): HashHolder {
        return HashList(attributes.map { attribute -> attribute.hashStruct() })
    }

    companion object : NetworkReadable<AttributeModifiersComponent> {

        override fun read(buffer: ByteBuf): AttributeModifiersComponent {
            return AttributeModifiersComponent(buffer.readList(Modifier::read))
        }
    }
}