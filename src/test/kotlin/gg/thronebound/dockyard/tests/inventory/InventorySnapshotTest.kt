package gg.thronebound.dockyard.tests.inventory

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.inventory.clearInventory
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.protocol.types.EquipmentSlot
import gg.thronebound.dockyard.registry.Items
import gg.thronebound.dockyard.ui.snapshot.InventorySnapshot
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class InventorySnapshotTest {

    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testInventorySnapshot() {
        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.clearInventory()
        
        player.equipment[EquipmentSlot.HELMET] = ItemStack(Items.NETHERITE_HELMET)
        player.equipment[EquipmentSlot.CHESTPLATE] = ItemStack(Items.NETHERITE_CHESTPLATE)
        player.equipment[EquipmentSlot.LEGGINGS] = ItemStack(Items.NETHERITE_LEGGINGS)
        player.equipment[EquipmentSlot.BOOTS] = ItemStack(Items.NETHERITE_BOOTS)

        player.inventory[0] = ItemStack(Items.DIAMOND_SWORD)
        player.heldSlotIndex.value = 0
        player.equipment[EquipmentSlot.OFF_HAND] = ItemStack(Items.TRIDENT)

        val snapshot = InventorySnapshot(player)
        player.clearInventory()

        assertEquals(null, player.equipment[EquipmentSlot.HELMET])
        assertEquals(null, player.equipment[EquipmentSlot.CHESTPLATE])
        assertEquals(null, player.equipment[EquipmentSlot.LEGGINGS])
        assertEquals(null, player.equipment[EquipmentSlot.BOOTS])
        assertEquals(ItemStack.AIR, player.inventory[0])
        assertEquals(null, player.equipment[EquipmentSlot.OFF_HAND])

        snapshot.restore()

        assertEquals(ItemStack(Items.NETHERITE_HELMET), player.equipment[EquipmentSlot.HELMET])
        assertEquals(ItemStack(Items.NETHERITE_CHESTPLATE), player.equipment[EquipmentSlot.CHESTPLATE])
        assertEquals(ItemStack(Items.NETHERITE_LEGGINGS), player.equipment[EquipmentSlot.LEGGINGS])
        assertEquals(ItemStack(Items.NETHERITE_BOOTS), player.equipment[EquipmentSlot.BOOTS])
        assertEquals(ItemStack(Items.DIAMOND_SWORD), player.inventory[0])
        assertEquals(ItemStack(Items.TRIDENT), player.equipment[EquipmentSlot.OFF_HAND])
        assertEquals(0, player.heldSlotIndex.value)

        snapshot.dispose()
    }
}