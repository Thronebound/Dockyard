package gg.thronebound.dockyard.entity

import cz.lukynka.bindables.Bindable
import gg.thronebound.dockyard.entity.metadata.EntityMetaValue
import gg.thronebound.dockyard.entity.metadata.EntityMetadata
import gg.thronebound.dockyard.entity.metadata.EntityMetadataType
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.registry.EntityTypes
import gg.thronebound.dockyard.registry.registries.EntityType
import gg.thronebound.dockyard.world.World

class ItemDisplay(location: Location, world: World): DisplayEntity(location) {

    override var type: EntityType = EntityTypes.ITEM_DISPLAY
    val item: Bindable<ItemStack> = Bindable(ItemStack.AIR)
    val renderType: Bindable<ItemDisplayRenderType> = Bindable(ItemDisplayRenderType.NONE)

    init {
        item.valueChanged {
            val type = EntityMetadataType.ITEM_DISPLAY_ITEM
            metadata[type] = EntityMetadata(type, EntityMetaValue.ITEM_STACK, item.value)
        }
        renderType.valueChanged {
            val type = EntityMetadataType.ITEM_DISPLAY_RENDER_TYPE
            metadata[type] = EntityMetadata(type, EntityMetaValue.BYTE, renderType.value.ordinal)
        }
    }
}

enum class ItemDisplayRenderType {
    NONE,
    THIRD_PERSON_LEFT_HAND,
    THIRD_PERSON_RIGHT_HAND,
    FIRST_PERSON_LEFT_HAND,
    FIRST_PERSON_RIGHT_HAND,
    HEAD,
    GUI,
    GROUND,
    FIXED
}