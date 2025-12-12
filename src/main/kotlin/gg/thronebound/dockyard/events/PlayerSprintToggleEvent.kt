package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player changes sprinting state")
data class PlayerSprintToggleEvent(val player: Player, val sprinting: Boolean, override val context: Event.Context) : Event