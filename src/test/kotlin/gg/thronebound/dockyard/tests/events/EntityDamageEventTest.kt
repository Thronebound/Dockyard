package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.entity.Parrot
import gg.thronebound.dockyard.events.EntityDamageEvent
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.registry.DamageTypes
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class EntityDamageEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val poorThing = Parrot(TestServer.testWorld.locationAt(0,0,0))
        val count = CountDownLatch(1)

        pool.on<EntityDamageEvent> {
            count.countDown()
        }

        poorThing.damage(0.1f, DamageTypes.SPIT)

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        poorThing.dispose()
        pool.dispose()
    }
}