package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.world.World

@EventDocumentation("when world is finished loading")
data class WorldFinishLoadingEvent(val world: World, override val context: Event.Context) : Event