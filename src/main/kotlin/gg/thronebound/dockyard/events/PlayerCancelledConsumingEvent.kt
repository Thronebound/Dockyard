package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player cancels consuming item")
data class PlayerCancelledConsumingEvent(val player: Player, val item: ItemStack, override val context: Event.Context) : Event