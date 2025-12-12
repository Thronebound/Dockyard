package gg.thronebound.dockyard.player

import gg.thronebound.dockyard.item.ItemStack

data class ItemInUse(
    var item: ItemStack,
    var startTime: Long,
    val time: Long,
)