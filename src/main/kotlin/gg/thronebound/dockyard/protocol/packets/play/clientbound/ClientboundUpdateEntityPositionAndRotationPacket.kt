package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.math.getRelativeCoords

class ClientboundUpdateEntityPositionAndRotationPacket(
    val entity: Entity,
    previousLocation: Location,
): ClientboundPacket() {

    init {
        val current = entity.location

        buffer.writeVarInt(entity.id)
        buffer.writeShort(getRelativeCoords(current.x, previousLocation.x))
        buffer.writeShort(getRelativeCoords(current.y, previousLocation.y))
        buffer.writeShort(getRelativeCoords(current.z, previousLocation.z))
        buffer.writeByte((entity.location.yaw * 256 / 360).toInt())
        buffer.writeByte((entity.location.pitch * 256 / 360).toInt())
        buffer.writeBoolean(entity.isOnGround)
    }
}