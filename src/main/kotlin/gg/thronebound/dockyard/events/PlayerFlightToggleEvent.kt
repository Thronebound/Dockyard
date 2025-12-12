package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player toggles flight")
data class PlayerFlightToggleEvent(val player: Player, val flying: Boolean, override val context: Event.Context): CancellableEvent()