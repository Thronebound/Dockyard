package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.item.ItemStack

@EventDocumentation("when item is given to an entity inventory")
data class InventoryGiveItemEvent(val entity: Entity, val item: ItemStack, val success: Boolean, override val context: Event.Context) : Event