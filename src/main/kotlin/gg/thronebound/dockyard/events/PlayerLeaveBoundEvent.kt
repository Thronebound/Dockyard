package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.apis.bounds.Bound
import gg.thronebound.dockyard.player.Player

@EventDocumentation("When player leaves a bound")
class PlayerLeaveBoundEvent(val player: Player, val bound: Bound, override val context: Event.Context) : Event