package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestFor
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.ItemGroupCooldownEndEvent
import gg.thronebound.dockyard.events.ItemGroupCooldownStartEvent
import gg.thronebound.dockyard.registry.Items
import gg.thronebound.dockyard.scheduler.runnables.ticks
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@TestFor(ItemGroupCooldownStartEvent::class, ItemGroupCooldownEndEvent::class)
class ItemGroupCooldownEventsTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val startCount = CountDownLatch(1)
        val endCount = CountDownLatch(1)

        pool.on<ItemGroupCooldownStartEvent> {
            startCount.countDown()
        }
        pool.on<ItemGroupCooldownEndEvent> {
            endCount.countDown()
        }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.setCooldown(Items.ENDER_PEARL, 1.ticks)

        assertTrue(startCount.await(5L, TimeUnit.SECONDS), "ItemGroupCooldownStartEvent didn't work")
        assertTrue(endCount.await(5L, TimeUnit.SECONDS), "ItemGroupCooldownEndEvent didn't work")
        pool.dispose()
    }
}