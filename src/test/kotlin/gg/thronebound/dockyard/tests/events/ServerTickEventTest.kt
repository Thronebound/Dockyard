package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.TestFor
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.ServerTickEvent
import gg.thronebound.dockyard.events.ServerTickMonitorEvent
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@TestFor(ServerTickEvent::class, ServerTickMonitorEvent::class)
class ServerTickEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)
        val monitorCount = CountDownLatch(1)

        // TestServer.server is already ticking
        pool.on<ServerTickEvent> {
            count.countDown()
        }
        pool.on<ServerTickMonitorEvent> {
            monitorCount.countDown()
        }

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        assertTrue(monitorCount.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}