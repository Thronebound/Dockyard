package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player's horse starts or stops jumping")
data class HorseJumpEvent(val player: Player, val isJumping: Boolean, override val context: Event.Context) : Event