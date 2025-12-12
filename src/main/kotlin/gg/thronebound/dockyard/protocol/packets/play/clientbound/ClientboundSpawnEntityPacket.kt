package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeUUID
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.location.writeLocation
import gg.thronebound.dockyard.location.writeRotation
import gg.thronebound.dockyard.math.vectors.Vector3d
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.utils.writeVelocity
import java.util.*

class ClientboundSpawnEntityPacket(
    entityId: Int,
    entityUUID: UUID,
    entityType: Int,
    location: Location,
    headYaw: Float,
    entityData: Int,
    velocity: Vector3d
) : ClientboundPacket() {

    init {
        buffer.writeVarInt(entityId)
        buffer.writeUUID(entityUUID)
        buffer.writeVarInt(entityType)
        buffer.writeLocation(location)
        buffer.writeRotation(location, true)
        buffer.writeByte((headYaw * 256 / 360).toInt())
        buffer.writeVarInt(entityData)
        buffer.writeVelocity(velocity)
    }
}