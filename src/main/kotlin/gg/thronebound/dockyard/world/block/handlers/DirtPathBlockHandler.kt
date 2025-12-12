package gg.thronebound.dockyard.world.block.handlers

import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.world.World
import gg.thronebound.dockyard.world.block.Block

class DirtPathBlockHandler : BlockHandler {
    override fun onUpdateByNeighbour(block: Block, world: World, location: Location, neighbour: Block, neighbourLocation: Location) {
        if(!location.add(0, 1, 0).block.isAir())
            location.setBlock(Blocks.DIRT)
    }
}