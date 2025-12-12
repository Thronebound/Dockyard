package gg.thronebound.dockyard.world.block

import gg.thronebound.dockyard.registry.registries.RegistryBlock
import net.kyori.adventure.nbt.CompoundBinaryTag

data class BlockEntity(
    val positionIndex: Int,
    val block: RegistryBlock,
    val data: CompoundBinaryTag,
) {
    val blockEntityTypeId get() = block.blockEntityId!!
}