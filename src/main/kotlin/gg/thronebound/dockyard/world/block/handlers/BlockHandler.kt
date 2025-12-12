package gg.thronebound.dockyard.world.block.handlers

import cz.lukynka.bindables.Bindable
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.world.Weather
import gg.thronebound.dockyard.world.World
import gg.thronebound.dockyard.world.block.Block

interface BlockHandler {

    fun onPlace(
        player: Player,
        heldItem: ItemStack,
        block: Block,
        face: Direction,
        location: Location,
        clickedBlock: Location,
        cursor: Vector3f
    ): Block? {
        return block
    }

    fun onDestroy(
        block: Block,
        world: World,
        location: Location
    ) {
        // Nothing by default
    }

    fun onUse(
        player: Player,
        hand: PlayerHand,
        heldItem: ItemStack,
        block: Block,
        face: Direction,
        location: Location,
        cursor: Vector3f
    ): Boolean {
        // Nothing by default
        return false
    }

    fun onAttack(
        player: Player,
        location: Location,
        block: Block,
        face: Direction,
    ) {
        // Nothing by default
    }

    fun onUpdateByNeighbour(block: Block, world: World, location: Location, neighbour: Block, neighbourLocation: Location) {
        // Nothing by default
    }

    fun onWeatherChange(block: Block, world: World, location: Location, change: Bindable.ValueChangedEvent<Weather>, isOccluded: Boolean) {
        // Nothing by default
    }

    fun onPowerOnByRedstone(block: Block, world: World, location: Location, power: Int) {
        // Nothing by default
    }

    fun onPowerOffByRedstone(block: Block, world: World, location: Location, power: Int) {
        // Nothing by default
    }

}