package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.location.writeLocation
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundBlockDestroyStagePacket(val breaker: Entity, val location: Location, val destroyStage: Int): ClientboundPacket() {

    init {
        buffer.writeVarInt(breaker.id)
        buffer.writeLocation(location)
        buffer.writeByte(destroyStage.coerceIn(0, 9))
    }

}