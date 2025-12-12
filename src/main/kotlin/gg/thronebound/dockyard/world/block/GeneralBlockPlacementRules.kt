package gg.thronebound.dockyard.world.block

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.registry.Blocks

object GeneralBlockPlacementRules {

    fun canBePlaced(originalClickedBlock: Location, where: Location, newBlock: Block, placer: Player): CancelReason {

        var canBePlaced = CancelReason(true, "")

        val world = originalClickedBlock.world

        val clickedBlock = world.getBlock(originalClickedBlock)
        val placementLocation = world.getBlock(where)

        if (!placementLocation.isAir() && placementLocation.registryBlock != Blocks.LIGHT && !placementLocation.registryBlock.isLiquid) canBePlaced = CancelReason(false, "Block at new location is not air (${placementLocation.identifier})")

        if (isLocationInsideBoundingBox(
                where,
                placer.world.entities
            ) && newBlock.registryBlock.isSolid
        ) canBePlaced = CancelReason(false, "Block collides with entity")
        if (!clickedBlock.registryBlock.isSolid) canBePlaced =
            CancelReason(false, "Block is not full block (${clickedBlock.identifier})")
        if (BlockDataHelper.isClickable(clickedBlock) && !placer.isSneaking) canBePlaced =
            CancelReason(false, "Block is clickable and player is not sneaking")

        return canBePlaced
    }

    data class CancelReason(
        var canBePlaced: Boolean,
        var reason: String,
    )

    //TODO Update entities to be stored inside chunk instead of all in the world and then check only the entities in the chunk
    private fun isLocationInsideBoundingBox(
        location: Location,
        entities: Collection<Entity>,
        toleranceY: Double = 0.2,
    ): Boolean {
        for (entity in entities) {
            val entityBoundingBox = entity.calculateBoundingBox()
            val insideX = entityBoundingBox.maxX > location.x && entityBoundingBox.minX < location.x + 1
            val insideZ = entityBoundingBox.maxZ > location.z && entityBoundingBox.minZ < location.z + 1
            val insideY = location.y >= entityBoundingBox.minY && location.y <= entityBoundingBox.maxY + toleranceY

            if (insideX && insideZ && insideY) {
                return true
            }
        }
        return false
    }
}