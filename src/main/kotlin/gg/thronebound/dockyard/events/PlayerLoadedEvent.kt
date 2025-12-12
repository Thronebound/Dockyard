package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player is fully loaded into a world")
data class PlayerLoadedEvent(val player: Player, override val context: Event.Context) : Event