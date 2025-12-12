package gg.thronebound.dockyard.tests.inventory

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.tests.assertSlot
import gg.thronebound.dockyard.tests.sendSlotClick
import gg.thronebound.dockyard.inventory.clearInventory
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ContainerClickMode
import gg.thronebound.dockyard.registry.Items
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class InventoryHotkeyTest {

    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
        PlayerTestUtil.getOrCreateFakePlayer().clearInventory()
    }

    @AfterTest
    fun cleanup() {
        PlayerTestUtil.getOrCreateFakePlayer().clearInventory()
    }

    @Test
    fun testHotkeyActions() {
        val player = PlayerTestUtil.getOrCreateFakePlayer()
        val itemStack1 = ItemStack(Items.TNT).withDisplayName("<red>tnt go booom").withAmount(6)
        val itemStack2 = ItemStack(Items.FLINT_AND_STEEL).withDisplayName("<yellow>thing that makes tnt go booom")

        player.inventory[0] = itemStack1
        player.inventory[1] = itemStack2

        sendSlotClick(player, 0, 3, ContainerClickMode.HOTKEY, itemStack1)
        assertSlot(player, 0, ItemStack.AIR)
        assertSlot(player, 3, itemStack1)

        sendSlotClick(player, 3, 1, ContainerClickMode.HOTKEY, itemStack1)

        assertSlot(player, 3, itemStack2)
        assertSlot(player, 1, itemStack1)
    }

    @Test
    fun testOffhandSwitch() {
        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.clearInventory()

        val itemStack1 = ItemStack(Items.TNT).withDisplayName("<red>tnt go booom").withAmount(6)
        val itemStack2 = ItemStack(Items.FLINT_AND_STEEL).withDisplayName("<yellow>thing that makes tnt go booom")

        player.inventory[0] = itemStack1
        player.inventory[1] = itemStack2

        sendSlotClick(player, 0, 40, ContainerClickMode.HOTKEY, itemStack1)

        assertSlot(player, 0, ItemStack.AIR)
        assertEquals(itemStack1, player.offHandItem)

        sendSlotClick(player, 1, 40, ContainerClickMode.HOTKEY, itemStack1)

        assertSlot(player, 1, itemStack1)
        assertEquals(itemStack2, player.offHandItem)

        sendSlotClick(player, 3, 40, ContainerClickMode.HOTKEY, itemStack2)
        assertEquals(ItemStack.AIR, player.offHandItem)
        assertSlot(player, 3, itemStack2)
    }
}