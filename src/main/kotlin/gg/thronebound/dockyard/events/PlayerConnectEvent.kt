package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("player connects to the server, before joining")
data class PlayerConnectEvent(val player: Player, override val context: Event.Context) : Event