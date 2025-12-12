package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.tests.sendSlotClick
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.InventoryClickEvent
import gg.thronebound.dockyard.inventory.clearInventory
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ContainerClickMode
import gg.thronebound.dockyard.protocol.packets.play.serverbound.NormalButtonAction
import gg.thronebound.dockyard.registry.Items
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class InventoryClickEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)
        val itemStack = Items.DANDELION.toItemStack(2).withDisplayName("pepeFlower")

        pool.on<InventoryClickEvent> {
            count.countDown()
        }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.inventory.cursorItem.value = itemStack

        sendSlotClick(player, 0, NormalButtonAction.LEFT_MOUSE_CLICK.button, ContainerClickMode.NORMAL, itemStack)
        assertTrue(count.await(5L, TimeUnit.SECONDS))

        pool.dispose()
        player.clearInventory()
    }
}