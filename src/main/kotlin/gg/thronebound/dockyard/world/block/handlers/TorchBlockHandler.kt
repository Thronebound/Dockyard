package gg.thronebound.dockyard.world.block.handlers

import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.world.block.Block

class TorchBlockHandler: BlockHandler {

    override fun onPlace(player: Player, heldItem: ItemStack, block: Block, face: Direction, location: Location, clickedBlock: Location, cursor: Vector3f): Block? {
        if(face == Direction.DOWN) return null
        if(face == Direction.UP) return block

        val final = when (block.registryBlock) {
            Blocks.TORCH -> Blocks.WALL_TORCH
            Blocks.SOUL_TORCH -> Blocks.SOUL_WALL_TORCH
            Blocks.REDSTONE_TORCH -> Blocks.REDSTONE_WALL_TORCH
            else -> block.registryBlock
        }

        return final.withBlockStates("facing" to face.name.lowercase())
    }
}