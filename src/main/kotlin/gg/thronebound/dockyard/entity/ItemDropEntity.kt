package gg.thronebound.dockyard.entity

import cz.lukynka.bindables.Bindable
import gg.thronebound.dockyard.entity.metadata.EntityMetaValue
import gg.thronebound.dockyard.entity.metadata.EntityMetadata
import gg.thronebound.dockyard.entity.metadata.EntityMetadataType
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.registry.EntityTypes
import gg.thronebound.dockyard.registry.registries.EntityType

class ItemDropEntity(override var location: Location, initialItem: ItemStack) : Entity(location) {

    override var type: EntityType = EntityTypes.ITEM
    override var inventorySize: Int = 0
    override val health: Bindable<Float> = Bindable(9999f)

    val itemStack: Bindable<ItemStack> = Bindable(initialItem)
    var canBePickedUp: Boolean = false
    var canBePickedUpAfter: Int = 20
    var pickupDistance: Int = 1
    var pickupAnimation: Boolean = true

    private var lifetime: Int = 0

    init {
        itemStack.valueChanged {
            val type = EntityMetadataType.ITEM_DROP_ITEM_STACK
            metadata[type] = EntityMetadata(type, EntityMetaValue.ITEM_STACK, it.newValue)
        }
        itemStack.triggerUpdate()
        if(canBePickedUpAfter == 0 || canBePickedUpAfter == -1) canBePickedUp = true
        hasNoGravity.value = true
    }

    override fun tick() {
        if(!canBePickedUp) {
            lifetime++
            if(lifetime == canBePickedUpAfter) {
                canBePickedUp = true
            }
        }
    }

    override fun dispose() {
        itemStack.dispose()
        super.dispose()
    }
}