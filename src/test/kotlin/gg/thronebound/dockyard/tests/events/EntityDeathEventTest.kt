package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.entity.Parrot
import gg.thronebound.dockyard.events.EntityDeathEvent
import gg.thronebound.dockyard.events.EventPool
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class EntityDeathEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<EntityDeathEvent> {
            count.countDown()
        }

        val entity = Parrot(TestServer.testWorld.locationAt(0,0,0))
        entity.kill()

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()

        entity.dispose()
    }
}