package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.registry.registries.DamageType

@EventDocumentation("when entity takes damage")
class EntityDamageEvent(val entity: Entity, var damage: Float, var damageType: DamageType, var attacker: Entity? = null, var projectile: Entity? = null, override val context: Event.Context) : CancellableEvent()