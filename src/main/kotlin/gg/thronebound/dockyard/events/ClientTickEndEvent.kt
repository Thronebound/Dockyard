package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when the current tick of client ends executing")
data class ClientTickEndEvent(val player: Player, override val context: Event.Context): Event