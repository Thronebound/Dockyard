package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.location.writeRotation
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.math.vectors.Vector3d

class ClientboundEntityTeleportPacket(entity: Entity, location: Location): ClientboundPacket() {

    constructor(entity: Entity) : this(entity, entity.location)

    init {
        buffer.writeVarInt(entity.id)
        location.toVector3d().write(buffer)
        Vector3d().write(buffer)
        buffer.writeRotation(location, false)
        buffer.writeInt(0x0000)
        buffer.writeBoolean(entity.isOnGround)
    }
}
