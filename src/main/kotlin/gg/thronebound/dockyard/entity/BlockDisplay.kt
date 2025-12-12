package gg.thronebound.dockyard.entity

import cz.lukynka.bindables.Bindable
import gg.thronebound.dockyard.entity.metadata.EntityMetaValue
import gg.thronebound.dockyard.entity.metadata.EntityMetadata
import gg.thronebound.dockyard.entity.metadata.EntityMetadataType
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.registry.EntityTypes
import gg.thronebound.dockyard.registry.registries.EntityType

class BlockDisplay(location: Location): DisplayEntity(location) {

    override var type: EntityType = EntityTypes.BLOCK_DISPLAY
    val block: Bindable<gg.thronebound.dockyard.world.block.Block> = Bindable(Blocks.STONE.toBlock())

    init {
        block.valueChanged {
            val type = EntityMetadataType.BLOCK_DISPLAY_BLOCK
            metadata[type] = EntityMetadata(type, EntityMetaValue.BLOCK_STATE, it.newValue)
        }
    }
}