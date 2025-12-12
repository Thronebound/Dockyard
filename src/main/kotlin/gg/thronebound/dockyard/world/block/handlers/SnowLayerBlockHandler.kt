package gg.thronebound.dockyard.world.block.handlers

import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.world.block.Block

class SnowLayerBlockHandler : BlockHandler {

    override fun onPlace(player: Player, heldItem: ItemStack, block: Block, face: Direction, location: Location, clickedBlock: Location, cursor: Vector3f): Block? {
        val blockBelow = location.subtract(0, 1, 0)

        if (blockBelow.block.registryBlock == block.registryBlock) {
            val layers = blockBelow.block.blockStates["layers"]?.toInt() ?: 1
            val newLayers = (layers + 1).coerceAtMost(8)

            val newBlock = if (newLayers == 8) {
                Blocks.SNOW_BLOCK.toBlock()
            } else {
                block.withBlockStates("layers" to newLayers.toString())
            }
            blockBelow.setBlock(newBlock)
            return null
        }

        return block.withBlockStates("layers" to "1")
    }
}