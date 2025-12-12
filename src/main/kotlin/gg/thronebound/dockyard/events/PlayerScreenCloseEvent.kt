package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.ui.Screen

@EventDocumentation("when screen (inventory gui) is closed")
data class PlayerScreenCloseEvent(val player: Player, val screen: Screen, override val context: Event.Context) : Event