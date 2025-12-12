package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.PlayerHand

@EventDocumentation("when player swings their hand")
data class PlayerSwingHandEvent(val player: Player, val hand: PlayerHand, override val context: Event.Context) : Event