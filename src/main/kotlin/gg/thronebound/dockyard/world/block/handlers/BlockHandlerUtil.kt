package gg.thronebound.dockyard.world.block.handlers

import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.world.block.Block
import gg.thronebound.dockyard.world.block.handlers.StairBlockHandler.Axis

object BlockHandlerUtil {
    fun getAxis(direction: Direction): Axis {
        val axis = when (direction) {
            Direction.DOWN,
            Direction.UP -> Axis.Y

            Direction.NORTH,
            Direction.SOUTH -> Axis.Z

            Direction.WEST,
            Direction.EAST -> Axis.X
        }
        return axis
    }

    fun getDirection(block: Block): Direction {
        val direction = block.blockStates["facing"] ?: "north"
        return Direction.valueOf(direction.uppercase())
    }

    fun rotateYCounterclockwise(direction: Direction): Direction {
        return when (direction.ordinal) {
            2 -> Direction.WEST
            5 -> Direction.NORTH
            3 -> Direction.EAST
            4 -> Direction.SOUTH
            else -> throw IllegalStateException("not supported rotation")
        }
    }

    fun rotateYClockwise(direction: Direction): Direction {
        return when (direction.ordinal) {
            2 -> Direction.EAST
            5 -> Direction.SOUTH
            3 -> Direction.WEST
            4 -> Direction.NORTH
            else -> throw IllegalStateException("not supported rotation")
        }
    }
}