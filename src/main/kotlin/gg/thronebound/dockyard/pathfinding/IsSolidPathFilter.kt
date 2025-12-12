package gg.thronebound.dockyard.pathfinding

import de.metaphoriker.pathetic.api.pathing.filter.PathFilter
import de.metaphoriker.pathetic.api.pathing.filter.PathValidationContext
import gg.thronebound.dockyard.pathfinding.PatheticPlatformDockyard.toLocation

class IsSolidPathFilter: PathFilter {

    override fun filter(context: PathValidationContext): Boolean {
        val location = context.position.toLocation()
        val block = location.block
        return PathfindingHelper.isTraversable(block, location)
    }
}