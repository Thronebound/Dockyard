package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player enters the configuration phase")
data class PlayerEnterConfigurationEvent(val player: Player, override val context: Event.Context) : Event