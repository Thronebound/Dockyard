package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket


class ClientboundPlayerAnimationPacket(entity: Entity, animation: EntityAnimation) : ClientboundPacket() {

    init {
        buffer.writeVarInt(entity.id)
        buffer.writeByte(animation.ordinal)
    }

}

enum class EntityAnimation {
    SWING_MAIN_ARM,
    LEAVE_BED,
    SWING_OFFHAND,
    CRITICAL_EFFECT,
    MAGIC_CRITICAL_EFFECT
}