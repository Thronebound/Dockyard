package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.math.vectors.Vector2f

class ClientboundUpdateEntityRotationPacket(
    entity: Entity,
    rotation: Vector2f,
) : ClientboundPacket() {

    init {
        buffer.writeVarInt(entity.id)
        buffer.writeByte((entity.location.yaw * 256 / 360).toInt())
        buffer.writeByte((entity.location.pitch * 256 / 360).toInt())
        buffer.writeBoolean(entity.isOnGround)
    }
}