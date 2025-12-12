package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.effects.AppliedPotionEffect
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.registry.registries.PotionEffect

class ClientboundRemoveEntityEffectPacket(entity: Entity, effect: PotionEffect) : ClientboundPacket() {

    constructor(entity: Entity, effect: AppliedPotionEffect) : this(entity, effect.effect)

    init {
        buffer.writeVarInt(entity.id)
        buffer.writeVarInt(effect.getProtocolId())
    }

}