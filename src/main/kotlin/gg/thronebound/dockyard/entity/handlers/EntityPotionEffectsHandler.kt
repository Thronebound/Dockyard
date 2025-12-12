package gg.thronebound.dockyard.entity.handlers

import cz.lukynka.bindables.BindableMap
import gg.thronebound.dockyard.effects.AppliedPotionEffect
import gg.thronebound.dockyard.effects.PotionEffectAttributes
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundEntityEffectPacket
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundRemoveEntityEffectPacket
import gg.thronebound.dockyard.registry.registries.PotionEffect
import gg.thronebound.dockyard.scheduler.runnables.inWholeMinecraftTicks

class EntityPotionEffectsHandler(override val entity: Entity) : TickableEntityHandler {

    fun handle(potionEffects: BindableMap<PotionEffect, AppliedPotionEffect>) {
        potionEffects.itemSet {
            it.value.startTime = System.currentTimeMillis()
            val packet = ClientboundEntityEffectPacket(
                entity,
                it.value.effect,
                it.value.settings.amplifier,
                it.value.settings.duration.inWholeMinecraftTicks,
                it.value.settings.showParticles,
                it.value.settings.isAmbient,
                it.value.settings.showIcon
            )

            entity.sendPacketToViewers(packet)
            entity.sendSelfPacketIfPlayer(packet)
            if (entity is Player) PotionEffectAttributes.onEffectApply(entity, it.value)
        }

        potionEffects.itemRemoved {
            val packet = ClientboundRemoveEntityEffectPacket(entity, it.value)
            entity.sendPacketToViewers(packet)
            if (entity is Player) PotionEffectAttributes.onEffectRemoved(entity, it.value.effect)
            entity.sendSelfPacketIfPlayer(packet)
        }
    }

    override fun tick() {
        entity.potionEffects.values.forEach { effect ->
            if (effect.value.settings.duration.isInfinite()) return@forEach
            if (System.currentTimeMillis() >= effect.value.startTime!! + effect.value.settings.duration.inWholeMilliseconds) {
                entity.potionEffects.remove(effect.key)
            }
        }
    }
}