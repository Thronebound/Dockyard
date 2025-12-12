package gg.thronebound.dockyard.world.generators

import gg.thronebound.dockyard.registry.registries.Biome
import gg.thronebound.dockyard.world.World
import gg.thronebound.dockyard.world.block.Block

interface WorldGenerator {
    fun getBlock(x: Int, y: Int, z: Int): Block

    fun getBiome(x: Int, y: Int, z: Int): Biome

    fun onWorldLoad(world: World) {}

    val generateBaseChunks: Boolean
        get() = true
}