package gg.thronebound.dockyard.world.generators

import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.math.vectors.Vector3
import gg.thronebound.dockyard.registry.Biomes
import gg.thronebound.dockyard.registry.registries.Biome
import gg.thronebound.dockyard.schematics.Schematic
import gg.thronebound.dockyard.world.World

class SchematicWorldGenerator(val schematic: Schematic, val origin: Vector3 = Vector3(), defaultBiome: Biome = Biomes.PLAINS) : VoidWorldGenerator(defaultBiome) {
    override fun onWorldLoad(world: World) {
        world.placeSchematic(schematic, Location(origin, 0f, 0f, world))
    }
}