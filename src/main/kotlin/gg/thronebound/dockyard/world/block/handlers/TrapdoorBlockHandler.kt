package gg.thronebound.dockyard.world.block.handlers

import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.player.*
import gg.thronebound.dockyard.registry.registries.BlockRegistry
import gg.thronebound.dockyard.world.block.Block

class TrapdoorBlockHandler : BlockHandler {

    override fun onPlace(player: Player, heldItem: ItemStack, block: Block, face: Direction, location: Location, clickedBlock: Location, cursor: Vector3f): Block? {
        val states = mutableMapOf<String, String>()

        states["half"] = if (cursor.y >= 0.5f && face != Direction.UP) "top" else "bottom"
        if (face == Direction.DOWN) states["half"] = "top"

        var dir = face
        if (face == Direction.UP || face == Direction.DOWN) dir = player.getDirection(true).getOpposite()
        states["facing"] = dir.name.lowercase()

        return block.withBlockStates(states)
    }

    override fun onUse(player: Player, hand: PlayerHand, heldItem: ItemStack, block: Block, face: Direction, location: Location, cursor: Vector3f): Boolean {
        if (player.isSneaking && !heldItem.isEmpty() && BlockRegistry.getEntries().keyToValue().containsKey(heldItem.material.identifier)) {
            return false
        }

        val newState = !(block.blockStates["open"]!!.toBoolean())

        player.world.setBlockState(location, "open" to newState.toString().lowercase())
        return true
    }
}