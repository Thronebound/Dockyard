package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player swaps offhand items")
data class PlayerSwapOffhandEvent(val player: Player, var mainHandItem: ItemStack, var offHandItem: ItemStack, override val context: Event.Context): CancellableEvent()