package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player dies")
data class PlayerDeathEvent(val player: Player, override val context: Event.Context) : CancellableEvent()