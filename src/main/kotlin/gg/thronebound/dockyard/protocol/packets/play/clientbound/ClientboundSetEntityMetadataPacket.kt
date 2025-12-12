package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.entity.metadata.EntityMetadata
import gg.thronebound.dockyard.entity.metadata.writeMetadata
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundSetEntityMetadataPacket(entity: Entity, metadata: Collection<EntityMetadata>) : ClientboundPacket() {

    init {
        buffer.writeVarInt(entity.id)
        metadata.forEach {
            buffer.writeMetadata(it)
        }
        // array end byte
        buffer.writeByte(0xFF)
    }
}