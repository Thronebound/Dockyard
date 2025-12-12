package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Player

@EventDocumentation("player moves or rotates their head")
data class PlayerMoveEvent(var oldLocation: Location, var newLocation: Location, var player: Player, var isOnlyHeadMovement: Boolean, override val context: Event.Context) : CancellableEvent()