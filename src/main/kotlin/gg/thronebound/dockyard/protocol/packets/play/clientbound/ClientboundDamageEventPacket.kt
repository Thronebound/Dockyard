package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.registry.registries.DamageType

class ClientboundDamageEventPacket(entity: Entity, type: DamageType, attacker: Entity?, projectile: Entity?, location: Location? = null) : ClientboundPacket() {

    init {
        buffer.writeVarInt(entity.id)
        buffer.writeVarInt(type.getProtocolId())
        buffer.writeVarInt(if (attacker != null) attacker.id + 1 else 0)
        var sourceDirectId = 0
        if (projectile != null) sourceDirectId = projectile.id
        if (projectile != null && attacker != null) {
            sourceDirectId = attacker.id
        }
        buffer.writeVarInt(sourceDirectId)
        buffer.writeBoolean(location != null)
        if (location != null) {
            buffer.writeDouble(location.x)
            buffer.writeDouble(location.y)
            buffer.writeDouble(location.z)
        }
    }
}