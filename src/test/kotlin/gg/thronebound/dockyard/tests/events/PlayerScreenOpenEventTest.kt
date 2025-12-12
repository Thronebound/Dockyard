package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestFor
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerScreenOpenEvent
import gg.thronebound.dockyard.ui.TestScreen
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@TestFor(PlayerScreenOpenEvent::class)
class PlayerScreenOpenEventTest {

    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<PlayerScreenOpenEvent> { count.countDown() }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        val screen = TestScreen()

        screen.open(player)

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
        screen.dispose()
    }
}