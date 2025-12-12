package gg.thronebound.dockyard.inventory

import gg.thronebound.dockyard.item.ItemStack

data class InventoryClickResult(
    var clicked: ItemStack,
    var cursor: ItemStack,
    var cancelled: Boolean
) {
    fun cancelled(): InventoryClickResult {
        cancelled = true
        return this
    }
}