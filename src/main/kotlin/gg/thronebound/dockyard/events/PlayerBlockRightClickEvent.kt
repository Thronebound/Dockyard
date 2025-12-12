package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.world.block.Block

@EventDocumentation("when player interacts with block")
data class PlayerBlockRightClickEvent(val player: Player, var heldItem: ItemStack, var block: Block, var face: Direction, var location: Location, override val context: Event.Context) : CancellableEvent()