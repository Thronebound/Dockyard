package gg.thronebound.dockyard.pathfinding

import de.metaphoriker.pathetic.api.provider.NavigationPoint
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.world.block.Block

class PathfindingNavigationPoint(val block: Block, val location: Location): NavigationPoint {

    override fun isTraversable(): Boolean {
        return PathfindingHelper.isTraversable(block, location)
    }
}