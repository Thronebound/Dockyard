package gg.thronebound.dockyard.world.block.handlers

import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.world.block.Block

class LanternBlockHandler: BlockHandler {

    override fun onPlace(player: Player, heldItem: ItemStack, block: Block, face: Direction, location: Location, clickedBlock: Location, cursor: Vector3f): Block? {
        val hanging = if (face == Direction.DOWN) "true" else "false"
        val final = block.withBlockStates("hanging" to hanging)
        if (face != Direction.DOWN && face != Direction.UP) {
            val blockBelow = location.world.getBlock(location.subtract(0, 1, 0))
            return if (blockBelow.registryBlock.isAir && blockBelow.registryBlock.isSolid) null else final
        }
        return final
    }
}