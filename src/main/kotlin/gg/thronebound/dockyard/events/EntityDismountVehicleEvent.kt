package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.entity.Entity

@EventDocumentation("when player tries to dismount from a vehicle")
data class EntityDismountVehicleEvent(val vehicle: Entity, val passenger: Entity, override val context: Event.Context) : CancellableEvent()