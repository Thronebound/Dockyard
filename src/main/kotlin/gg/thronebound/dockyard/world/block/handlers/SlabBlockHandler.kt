package gg.thronebound.dockyard.world.block.handlers

import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.toNormalizedVector3f
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.world.block.Block

class SlabBlockHandler : BlockHandler {

    override fun onPlace(player: Player, heldItem: ItemStack, block: Block, face: Direction, location: Location, clickedBlock: Location, cursor: Vector3f): Block? {
        var existingBlockLocation = clickedBlock.add(face.toNormalizedVector3f())
        var existingBlock = existingBlockLocation.block
        val states = mutableMapOf<String, String>()

        states["type"] = if (cursor.y >= 0.5f && face != Direction.UP) "top" else "bottom"
        if (face == Direction.DOWN) states["type"] = "top"

        if (clickedBlock.block.identifier == block.identifier
            && clickedBlock.block.blockStates["type"] != "double"
            && (face == Direction.DOWN || face == Direction.UP)
        ) {

            if ((clickedBlock.block.blockStates["type"] == "top" && face == Direction.DOWN) ||
                (clickedBlock.block.blockStates["type"] == "bottom" && face == Direction.UP)
            ) {

                existingBlock = clickedBlock.block
                existingBlockLocation = clickedBlock
            }
        }

        if (existingBlock.blockStates["type"] == "bottom" && existingBlock.identifier == block.identifier) {
            clickedBlock.world.setBlockState(existingBlockLocation, "type" to "double")
            return null
        }

        if (existingBlock.blockStates["type"] == "top" && existingBlock.identifier == block.identifier) {
            clickedBlock.world.setBlockState(existingBlockLocation, "type" to "double")
            return null
        }

        return block.withBlockStates(states)
    }
}