package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestFor
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.ServerSendPlayerMessageEvent
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@TestFor(ServerSendPlayerMessageEvent::class)
class ServerSendPlayerMessageEventTest {

    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<ServerSendPlayerMessageEvent> {
            count.countDown()
        }

        PlayerTestUtil.getOrCreateFakePlayer().sendMessage(":3")

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }

    @Test
    fun testEventDoesNotFire() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<ServerSendPlayerMessageEvent> {
            count.countDown()
        }
        PlayerTestUtil.getOrCreateFakePlayer().sendActionBar("not :3")

        count.await(2L, TimeUnit.SECONDS)
        assertTrue { count.count == 1L }
        pool.dispose()
    }
}