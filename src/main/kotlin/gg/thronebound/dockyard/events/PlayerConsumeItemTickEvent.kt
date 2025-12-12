package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.player.Player

@EventDocumentation("every tick when player is consuming item")
data class PlayerConsumeItemTickEvent(val player: Player, val item: ItemStack, val tick: Int, override val context: Event.Context) : Event