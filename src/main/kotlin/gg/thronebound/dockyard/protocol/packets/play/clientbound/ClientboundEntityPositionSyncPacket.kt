package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.math.vectors.Vector3d

class ClientboundEntityPositionSyncPacket(val entity: Entity, val location: Location, val delta: Vector3d, val isOnGround: Boolean): ClientboundPacket() {
    init {
        buffer.writeVarInt(entity.id)
        location.toVector3d().write(buffer)
        delta.write(buffer)
        buffer.writeFloat(location.yaw)
        buffer.writeFloat(location.pitch)
        buffer.writeBoolean(isOnGround)
    }
}