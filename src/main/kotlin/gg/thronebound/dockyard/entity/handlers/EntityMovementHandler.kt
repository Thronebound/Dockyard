package gg.thronebound.dockyard.entity.handlers

import gg.thronebound.dockyard.entity.Entity

class EntityMovementHandler(override val entity: Entity) : TickableEntityHandler {

    override fun tick() {
        entity.gravityTickCount = if (entity.isOnGround) 0 else entity.gravityTickCount + 1
    }
}