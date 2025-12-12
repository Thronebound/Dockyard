package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player starts consuming item")
data class PlayerStartConsumingEvent(val player: Player, val item: ItemStack, override val context: Event.Context) : CancellableEvent()