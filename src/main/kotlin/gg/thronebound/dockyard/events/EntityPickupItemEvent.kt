package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.entity.ItemDropEntity

@EventDocumentation("when entity pickups dropped item")
data class EntityPickupItemEvent(val entity: Entity, var itemDropEntity: ItemDropEntity, override val context: Event.Context): CancellableEvent()