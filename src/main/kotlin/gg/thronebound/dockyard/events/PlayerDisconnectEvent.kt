package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player disconnects")
data class PlayerDisconnectEvent(val player: Player, override val context: Event.Context) : Event