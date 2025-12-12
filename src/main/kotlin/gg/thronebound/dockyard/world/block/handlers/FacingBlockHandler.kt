package gg.thronebound.dockyard.world.block.handlers

import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.getDirection
import gg.thronebound.dockyard.player.getOpposite
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.world.block.Block

class FacingBlockHandler(val upDown: Boolean) : BlockHandler {

    override fun onPlace(player: Player, heldItem: ItemStack, block: Block, face: Direction, location: Location, clickedBlock: Location, cursor: Vector3f): Block? {
        val direction = if (upDown || !(face == Direction.UP || face == Direction.DOWN)) {
            face
        } else {
            player.getDirection(true).getOpposite()
        }
        return block.withBlockStates("facing" to direction.name.lowercase())
    }

}