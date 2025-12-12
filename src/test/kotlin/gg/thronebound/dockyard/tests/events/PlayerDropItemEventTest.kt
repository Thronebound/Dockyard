package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.entity.EntityManager.despawnEntity
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerDropItemEvent
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.registry.Items
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerDropItemEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.inventory[0] = Items.DIAMOND.toItemStack()

        pool.on<PlayerDropItemEvent> {
            count.countDown()
        }

        player.inventory.drop(player.inventory[0])

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()

        player.world.entities.forEach {
            if(it !is Player) {
                player.world.despawnEntity(it)
            }
        }
    }
}