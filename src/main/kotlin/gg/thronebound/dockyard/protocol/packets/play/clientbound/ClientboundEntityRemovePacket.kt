package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.extentions.writeVarIntArray
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundEntityRemovePacket(entities: MutableList<Entity>) : ClientboundPacket() {
    constructor(entity: Entity) : this(mutableListOf(entity))

    init {
        buffer.writeVarIntArray(entities.map { it.id })
    }
}