package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player's sneaking state changes")
data class PlayerSneakToggleEvent(val player: Player, val sneaking: Boolean, override val context: Event.Context) : Event