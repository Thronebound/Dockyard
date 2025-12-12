package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.registry.registries.DamageType

@EventDocumentation("when player is damaged")
data class PlayerDamageEvent(val player: Player, var damage: Float, var damageType: DamageType, var attacker: Entity? = null, var projectile: Entity? = null, override val context: Event.Context) : CancellableEvent()