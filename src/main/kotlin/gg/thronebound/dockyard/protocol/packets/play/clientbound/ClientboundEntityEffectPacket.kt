package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.registry.registries.PotionEffect
import kotlin.experimental.or

class ClientboundEntityEffectPacket(
    var entity: Entity,
    var effect: PotionEffect,
    var amplifier: Int,
    var duration: Int,
    var showParticles: Boolean = false,
    var showBlueBorder: Boolean = false,
    var showIconOnHud: Boolean = true,
) : ClientboundPacket() {

    init {
        val flags: Byte = 0
        if (showBlueBorder) flags or 0x01
        if (showParticles) flags or 0x02
        if (showIconOnHud) flags or 0x04

        amplifier -= 1
        if (amplifier <= -1) amplifier = 0

        buffer.writeVarInt(entity.id)
        buffer.writeVarInt(effect.getProtocolId())
        buffer.writeVarInt(amplifier)
        buffer.writeVarInt(duration)
        buffer.writeByte(flags.toInt())
    }
}