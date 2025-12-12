package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.attributes.AttributeModifier
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.extentions.readList
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.NetworkWritable
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.protocol.types.writeList
import gg.thronebound.dockyard.registry.registries.Attribute
import gg.thronebound.dockyard.registry.registries.AttributeRegistry
import io.netty.buffer.ByteBuf

class ClientboundUpdateAttributesPacket(val entity: Entity, val properties: Collection<Property>) : ClientboundPacket() {

    init {
        buffer.writeVarInt(entity.id)
        buffer.writeList(properties, Property::write)
    }

    data class Property(val attribute: Attribute, val value: Double, val modifiers: Collection<AttributeModifier>) : NetworkWritable {

        override fun write(buffer: ByteBuf) {
            buffer.writeVarInt(attribute.getProtocolId())
            buffer.writeDouble(value)
            buffer.writeList(modifiers, AttributeModifier::write)
        }

        companion object : NetworkReadable<Property> {

            override fun read(buffer: ByteBuf): Property {
                return Property(
                    attribute = AttributeRegistry.getByProtocolId(buffer.readVarInt()),
                    value = buffer.readDouble(),
                    modifiers = buffer.readList { b -> AttributeModifier.STREAM_CODEC.read(b) }
                )
            }
        }
    }
}