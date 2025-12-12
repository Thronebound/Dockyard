package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.world.World

@EventDocumentation("when entity is spawned in a world")
data class EntitySpawnEvent(val entity: Entity, val world: World, override val context: Event.Context) : CancellableEvent()