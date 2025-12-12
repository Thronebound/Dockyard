package gg.thronebound.dockyard.world.block.handlers

import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.getDirection
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.world.World
import gg.thronebound.dockyard.world.block.Block

class DoorBlockHandler: BlockHandler {

    override fun onPlace(player: Player, heldItem: ItemStack, block: Block, face: Direction, location: Location, clickedBlock: Location, cursor: Vector3f): Block? {
        val states = mutableMapOf<String, String>()

        val direction = player.getDirection(true)
        states["facing"] = direction.name.lowercase()

        val blockAbove = location.add(0, 1, 0)
        location.world.setBlock(blockAbove, block.withBlockStates("half" to "upper").withBlockStates(states))
        return block.withBlockStates(states)
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

    override fun onUse(player: Player, hand: PlayerHand, heldItem: ItemStack, block: Block, face: Direction, location: Location, cursor: Vector3f): Boolean {
        if(player.isSneaking) return false
        if(block.registryBlock == Blocks.IRON_DOOR) return false
        val blockAbove = location.add(0, 1, 0)
        val blockBelow = location.add(0, -1, 0)

        val newOpenState = (!block.blockStates["open"].toBoolean()).toString()
        location.world.setBlockState(location, "open" to newOpenState)

        if(blockAbove.block.registryBlock == block.registryBlock && blockAbove.block.blockStates["half"] == "upper") {
            blockAbove.world.setBlockState(blockAbove, "open" to newOpenState)
        }

        if(blockBelow.block.registryBlock == block.registryBlock && blockBelow.block.blockStates["half"] == "lower") {
            blockBelow.world.setBlockState(blockBelow, "open" to newOpenState)
        }

        return true
    }
}