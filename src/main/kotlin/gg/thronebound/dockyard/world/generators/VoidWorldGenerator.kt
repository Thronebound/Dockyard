package gg.thronebound.dockyard.world.generators

import gg.thronebound.dockyard.registry.Biomes
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.registry.registries.Biome

open class VoidWorldGenerator(val defaultBiome: Biome = Biomes.THE_VOID): WorldGenerator {
    override fun getBlock(x: Int, y: Int, z: Int): gg.thronebound.dockyard.world.block.Block = Blocks.AIR.toBlock()

    override fun getBiome(x: Int, y: Int, z: Int): Biome = defaultBiome

    override val generateBaseChunks: Boolean
        get() = true
}