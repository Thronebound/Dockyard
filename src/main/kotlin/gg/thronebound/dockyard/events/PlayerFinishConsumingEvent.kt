package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player finishes consuming item")
data class PlayerFinishConsumingEvent(val player: Player, val item: ItemStack, override val context: Event.Context) : Event