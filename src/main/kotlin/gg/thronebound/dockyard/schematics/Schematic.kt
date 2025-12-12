@file:Suppress("ArrayInDataClass")

package gg.thronebound.dockyard.schematics

import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.math.vectors.Vector3
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.world.block.Block
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.kyori.adventure.nbt.CompoundBinaryTag

data class Schematic(
    var size: Vector3,
    var offset: Vector3,
    var palette: Object2IntOpenHashMap<Block>,
    var blocks: ByteArray,
    var blockEntities: Map<Vector3, CompoundBinaryTag>
) {

    sealed interface SchematicBlock {

        data class Normal(val localSpaceLocation: Location, val location: Location, val id: Int) : SchematicBlock

        data class BlockEntity(val localSpaceLocation: Location, val location: Location, val block: Block, val data: CompoundBinaryTag) : SchematicBlock
    }

    companion object {
        val empty = Schematic(Vector3(), Vector3(), Object2IntOpenHashMap(), ByteArray(0), mapOf())
        val RED_STAINED_GLASS = Blocks.RED_STAINED_GLASS.toBlock()
    }
}



