package gg.thronebound.dockyard.world.block.handlers

import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.registry.registries.RegistryBlock
import gg.thronebound.dockyard.world.World
import gg.thronebound.dockyard.world.block.Block

class DoublePlantBlockHandler : BlockHandler {
    companion object {
        val doublePlants: List<RegistryBlock> = listOf(
            Blocks.SUNFLOWER,
            Blocks.LILAC,
            Blocks.ROSE_BUSH,
            Blocks.PEONY,
            Blocks.TALL_GRASS,
            Blocks.LARGE_FERN
        )
    }

    override fun onPlace(player: Player, heldItem: ItemStack, block: Block, face: Direction, location: Location, clickedBlock: Location, cursor: Vector3f): Block? {
        val blockAbove = location.add(0, 1, 0)
        location.world.setBlock(blockAbove, block.withBlockStates("half" to "upper"))
        return block.withBlockStates("half" to "lower")
    }

    override fun onDestroy(block: Block, world: World, location: Location) {
        val blockAbove = location.add(0, 1, 0)
        val blockBelow = location.add(0, -1, 0)
        if(blockAbove.block.registryBlock == block.registryBlock && blockAbove.block.blockStates["half"] == "upper") {
            blockAbove.world.destroyNaturally(blockAbove)
        }
        if(blockBelow.block.registryBlock == block.registryBlock && blockBelow.block.blockStates["half"] == "lower") {
            blockBelow.world.destroyNaturally(blockBelow)
        }
    }
}