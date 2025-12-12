package gg.thronebound.dockyard.world.block.handlers

import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.registry.Tags
import gg.thronebound.dockyard.world.block.Block

class DirtBlockHandler : BlockHandler {
    override fun onUse(player: Player, hand: PlayerHand, heldItem: ItemStack, block: Block, face: Direction, location: Location, cursor: Vector3f): Boolean {
        if(!Tags.ITEM_SHOVELS.contains(heldItem.material.identifier)) return false
        if(!location.add(0, 1, 0).block.isAir()) return false

        location.setBlock(Blocks.DIRT_PATH)
        return true
    }
}
