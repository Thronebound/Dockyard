package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.inventory.EntityInventory
import gg.thronebound.dockyard.item.ItemStack

@EventDocumentation("when a slot is set in entity's inventory")
data class InventoryItemChangeEvent(
    val entity: Entity,
    val inventory: EntityInventory,
    var slot: Int,
    var newItem: ItemStack,
    var oldItem: ItemStack,
    override val context: Event.Context
) : Event