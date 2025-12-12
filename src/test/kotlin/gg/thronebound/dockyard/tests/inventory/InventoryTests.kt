package gg.thronebound.dockyard.tests.inventory

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.tests.assertSlot
import gg.thronebound.dockyard.inventory.clearInventory
import gg.thronebound.dockyard.inventory.give
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundCloseContainerPacket
import gg.thronebound.dockyard.registry.Items
import gg.thronebound.dockyard.registry.registries.ItemRegistry
import gg.thronebound.dockyard.ui.TestScreen
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class InventoryTests {

    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @AfterTest
    fun cleanup() {
    }

    @Test
    fun testInventoryOpen() {
        val player = PlayerTestUtil.getOrCreateFakePlayer()
        val screen = TestScreen()
        assertEquals(null, player.currentlyOpenScreen)
        assertEquals(false, player.hasInventoryOpen)

        screen.open(player)
        assertEquals(true, player.currentlyOpenScreen is TestScreen)
        assertEquals(true, player.hasInventoryOpen)

        PlayerTestUtil.sendPacket(player, ServerboundCloseContainerPacket(1))

        assertEquals(null, player.currentlyOpenScreen)
        assertEquals(false, player.hasInventoryOpen)
    }

    @Test
    fun testInventoryClose() {
        val player = PlayerTestUtil.getOrCreateFakePlayer()
        val itemStack = ItemStack(Items.SWEET_BERRIES, 5)
        player.clearInventory()

        player.inventory.cursorItem.value = itemStack
        player.closeInventory()

        assertSlot(player, 0, itemStack)
        assertEquals(ItemStack.AIR, player.inventory.cursorItem.value)
    }

    @Test
    fun testGive() {
        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.clearInventory()

        val itemStack = ItemStack(Items.COOKIE).withAmount(2).withDisplayName("<orange>Magical cookie")

        player.give(Items.STONE)
        player.give(Items.TNT)
        player.give(Items.FLINT_AND_STEEL)
        player.give(Items.FLINT_AND_STEEL)
        player.give(itemStack)

        for (i in 0 until 4) {
            player.give(ItemRegistry.getEntries().keyToValue().values.random())
        }
        player.give(itemStack)

        assertSlot(player, 0, Items.STONE)
        assertSlot(player, 1, Items.TNT)
        assertSlot(player, 2, Items.FLINT_AND_STEEL)
        assertSlot(player, 3, Items.FLINT_AND_STEEL)
        assertSlot(player, 4, itemStack.withAmount(4))

        player.clearInventory()
        player.inventory[3] = itemStack

        player.give(itemStack)

        assertSlot(player, 0, ItemStack.AIR)
        assertSlot(player, 3, itemStack.withAmount(4))
    }
}
