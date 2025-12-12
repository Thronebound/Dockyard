package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.math.vectors.Vector3d
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.utils.writeVelocity

class ClientboundSetEntityVelocityPacket(entity: Entity, velocity: Vector3d) : ClientboundPacket() {

    init {
        buffer.writeVarInt(entity.id)
        buffer.writeVelocity(velocity)
    }

}

