package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.WorldTickEvent
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class WorldTickEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        // TestServer.testWorld is ticking
        pool.on<WorldTickEvent> {
            count.countDown()
        }

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}