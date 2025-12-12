package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerChangeWorldEvent
import gg.thronebound.dockyard.registry.Biomes
import gg.thronebound.dockyard.registry.DimensionTypes
import gg.thronebound.dockyard.world.WorldManager
import gg.thronebound.dockyard.world.generators.VoidWorldGenerator
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerChangeWorldEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)
        val player = PlayerTestUtil.getOrCreateFakePlayer()
        val world = WorldManager.create("playerchangeworldeventtest", VoidWorldGenerator(Biomes.DESERT), DimensionTypes.OVERWORLD)

        pool.on<PlayerChangeWorldEvent> {
            if (it.newWorld == world) {
                count.countDown()
            }
        }
        world.join(player)

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
        WorldManager.delete(world)
    }
}