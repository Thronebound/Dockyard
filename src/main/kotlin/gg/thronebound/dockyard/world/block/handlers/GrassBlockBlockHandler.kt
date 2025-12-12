package gg.thronebound.dockyard.world.block.handlers

import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.world.World
import gg.thronebound.dockyard.world.block.Block

class GrassBlockBlockHandler : BlockHandler {
    override fun onUpdateByNeighbour(block: Block, world: World, location: Location, neighbour: Block, neighbourLocation: Location) {
        if(location.add(0, 1, 0) != neighbourLocation) return

        val isSnowy = neighbourLocation.block.identifier.contains("snow")

        if(block.blockStates["snowy"] != isSnowy.toString()) {
            location.setBlock(block.withBlockStates("snowy" to isSnowy.toString()))
        }
    }
}