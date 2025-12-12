package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.apis.bounds.Bound
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player enters a bound")
data class PlayerEnterBoundEvent(val player: Player, val bound: Bound, override val context: Event.Context) : Event