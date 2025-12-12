package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player drops item")
data class PlayerDropItemEvent(val player: Player, var itemStack: ItemStack, override val context: Event.Context) : CancellableEvent()