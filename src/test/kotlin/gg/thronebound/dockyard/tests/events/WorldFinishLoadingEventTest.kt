package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.WorldFinishLoadingEvent
import gg.thronebound.dockyard.registry.Biomes
import gg.thronebound.dockyard.registry.DimensionTypes
import gg.thronebound.dockyard.world.WorldManager
import gg.thronebound.dockyard.world.generators.VoidWorldGenerator
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class WorldFinishLoadingEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<WorldFinishLoadingEvent> {
            count.countDown()
        }

        val world = WorldManager.create("world_finish_loading_event_test", VoidWorldGenerator(Biomes.THE_VOID), DimensionTypes.THE_END)

        assertTrue(count.await(5L, TimeUnit.SECONDS))

        pool.dispose()
        WorldManager.delete(world)
    }
}