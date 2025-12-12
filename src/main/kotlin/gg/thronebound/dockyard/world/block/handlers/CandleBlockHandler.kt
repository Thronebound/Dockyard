package gg.thronebound.dockyard.world.block.handlers

import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.registry.Items
import gg.thronebound.dockyard.world.block.Block

class CandleBlockHandler : BlockHandler {
    override fun onPlace(player: Player, heldItem: ItemStack, block: Block, face: Direction, location: Location, clickedBlock: Location, cursor: Vector3f): Block? {
        val theBlock: Location = if (clickedBlock.block.registryBlock == block.registryBlock && clickedBlock.block.blockStates["candles"] != "4") {
            clickedBlock
        } else if (location.block.registryBlock == block.registryBlock) {
            location
        } else {
            return block
        }

        var candles = theBlock.block.blockStates["candles"]?.toIntOrNull() ?: 1
        candles = (candles + 1).coerceAtMost(4)

        theBlock.setBlock(theBlock.block.withBlockStates("candles" to candles.toString()))
        return null
    }

    override fun onUse(player: Player, hand: PlayerHand, heldItem: ItemStack, block: Block, face: Direction, location: Location, cursor: Vector3f): Boolean {
        if(heldItem.material == Items.FLINT_AND_STEEL && block.blockStates["lit"] != "true") {
            location.setBlock(block.withBlockStates("lit" to "true"))
            return true
        }
        return false
    }
}
