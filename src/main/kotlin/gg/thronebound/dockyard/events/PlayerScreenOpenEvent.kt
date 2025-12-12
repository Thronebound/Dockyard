package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.ui.Screen

@EventDocumentation("when a screen (inventory gui) is open to a player")
data class PlayerScreenOpenEvent(val player: Player, val screen: Screen, override val context: Event.Context) : CancellableEvent()