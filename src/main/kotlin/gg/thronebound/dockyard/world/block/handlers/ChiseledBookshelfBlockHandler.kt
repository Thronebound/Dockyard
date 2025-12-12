package gg.thronebound.dockyard.world.block.handlers

import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.player.systems.GameMode
import gg.thronebound.dockyard.registry.Tags
import gg.thronebound.dockyard.world.block.Block
import kotlin.math.roundToInt

class ChiseledBookshelfBlockHandler : BlockHandler {
    override fun onUse(player: Player, hand: PlayerHand, heldItem: ItemStack, block: Block, face: Direction, location: Location, cursor: Vector3f): Boolean {
        if(player.gameMode.value == GameMode.ADVENTURE) return false
        val facing = block.blockStates["facing"] ?: return false
        if (face.name.lowercase() != facing) return false

        // starts from the top
        val row = 1 - cursor.y.roundToInt()

        val column: Int = when (face) {
            Direction.NORTH -> 1 - cursor.x
            Direction.SOUTH -> cursor.x
            Direction.WEST -> cursor.z
            Direction.EAST -> 1 - cursor.z
            else -> throw IllegalStateException("chiseled bookshelf cannot face $face")
        }.let { (it * 2).roundToInt() }

        val slot = "slot_${row * 3 + column}_occupied"
        val occupied = block.blockStates[slot] == "true"

        if (!(occupied || Tags.ITEM_BOOKSHELF_BOOKS.contains(heldItem.material.identifier))) {
            return false
        }
        location.setBlock(block.withBlockStates(slot to (!occupied).toString()))

        return true
    }
}
