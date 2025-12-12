package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerDamageEvent
import gg.thronebound.dockyard.registry.DamageTypes
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerDamageEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)
        val player = PlayerTestUtil.getOrCreateFakePlayer()

        pool.on<PlayerDamageEvent> {
            it.cancelled = true
            count.countDown()
        }
        player.damage(0.1f, DamageTypes.SPIT)

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}
