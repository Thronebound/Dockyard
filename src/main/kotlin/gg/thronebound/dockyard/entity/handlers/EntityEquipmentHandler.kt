package gg.thronebound.dockyard.entity.handlers

import cz.lukynka.bindables.BindableMap
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.player.PersistentPlayer
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.types.EquipmentSlot

class EntityEquipmentHandler(override val entity: Entity) : EntityHandler {

    fun handle(
        equipment: BindableMap<EquipmentSlot, ItemStack>,
        equipmentLayers: BindableMap<PersistentPlayer, Map<EquipmentSlot, ItemStack>>,
    ) {
        equipment.itemSet {
            if (entity !is Player) return@itemSet
            entity.inventory.unsafeUpdateEquipmentSlot(it.key, entity.heldSlotIndex.value, it.value)
        }

        equipment.mapUpdated {
            if (entity is Player) entity.sendEquipmentPacket(entity)
            entity.viewers.forEach { viewer -> entity.sendEquipmentPacket(viewer) }
        }

        equipmentLayers.itemSet {
            val player = it.key.toPlayer()
            if (player != null) entity.sendEquipmentPacket(player)
        }

        equipmentLayers.itemRemoved {
            val player = it.key.toPlayer()
            if (player != null) entity.sendEquipmentPacket(player)
        }
    }
}