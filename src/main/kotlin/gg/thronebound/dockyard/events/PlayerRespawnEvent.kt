package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player respawns after dying")
data class PlayerRespawnEvent(val player: Player, val isBecauseOfDeath: Boolean, override val context: Event.Context): Event