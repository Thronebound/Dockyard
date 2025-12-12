package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.pathfinding.Navigator

@EventDocumentation("when navigator selects locations in a path to follow. You can use this event to add slight offset to your path for example")
data class EntityNavigatorPickOffsetEvent(val entity: Entity, val navigator: Navigator, var location: Location, override val context: Event.Context) : Event