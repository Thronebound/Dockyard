package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.types.EquipmentSlot

@EventDocumentation("when player equips piece of equipment")
data class PlayerEquipEvent(
    val player: Player,
    var item: ItemStack,
    val slot: EquipmentSlot,
    override val context: Event.Context
) : Event