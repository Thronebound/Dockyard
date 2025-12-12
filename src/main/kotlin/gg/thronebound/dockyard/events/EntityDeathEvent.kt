package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.entity.Entity

@EventDocumentation("when entity dies")
data class EntityDeathEvent(val entity: Entity, override val context: Event.Context) : CancellableEvent()