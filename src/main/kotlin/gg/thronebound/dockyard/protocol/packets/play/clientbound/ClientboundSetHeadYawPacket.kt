package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundSetHeadYawPacket(entity: Entity, location: Location = entity.location) : ClientboundPacket() {

    init {
        buffer.writeVarInt(entity.id)
        buffer.writeByte(((location.yaw % 360) * 256 / 360).toInt())
    }
}