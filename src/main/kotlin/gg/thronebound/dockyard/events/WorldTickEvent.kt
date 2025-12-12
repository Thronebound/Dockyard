package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.scheduler.CustomRateScheduler
import gg.thronebound.dockyard.world.World

@EventDocumentation("when world ticks")
data class WorldTickEvent(val world: World, val scheduler: CustomRateScheduler, override val context: Event.Context) : CancellableEvent()