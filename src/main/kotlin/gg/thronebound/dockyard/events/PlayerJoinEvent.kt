package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player enters the PLAY phase")
data class PlayerJoinEvent(val player: Player, override val context: Event.Context) : Event