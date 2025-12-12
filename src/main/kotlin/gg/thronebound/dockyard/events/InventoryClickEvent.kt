package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player clicks in inventory")
data class InventoryClickEvent(val player: Player, override val context: Event.Context) : Event