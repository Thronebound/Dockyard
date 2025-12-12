package gg.thronebound.dockyard.tests.inventory

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.inventory.clearInventory
import gg.thronebound.dockyard.inventory.give
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.registry.Items
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class InventoryGiveTests {

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
    fun testGiveItemToPlayer() {
        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.clearInventory()
        val tnt = ItemStack(Items.TNT)

        player.give(tnt)
        assertEquals(tnt, player.inventory[0])
    }

    @Test
    fun testGiveItemOverStackSize() {
        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.clearInventory()
        val tnt = ItemStack(Items.TNT)

        player.give(tnt.withAmount(60))
        assertEquals(tnt.withAmount(60), player.inventory[0])

        player.give(tnt.withAmount(8))
        assertEquals(tnt.withAmount(64), player.inventory[0])
        assertEquals(tnt.withAmount(4), player.inventory[1])
    }
}