package gg.thronebound.dockyard.inventory

import gg.thronebound.dockyard.item.ItemStack

interface ContainerInventory {
    var name: String
    var rows: Int
    var contents: MutableMap<Int, ItemStack>
}