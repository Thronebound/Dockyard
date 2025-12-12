package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestFor
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.apis.bounds.Bound
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerEnterBoundEvent
import gg.thronebound.dockyard.events.PlayerLeaveBoundEvent
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@TestFor(PlayerEnterBoundEvent::class, PlayerLeaveBoundEvent::class)
class PlayerEnterLeaveBoundEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventsFire() {
        val pool = EventPool()
        val enterCount = CountDownLatch(1)
        val leaveCount = CountDownLatch(1)

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        val bound: Bound = Bound(
            player.location.add(4, -1, -8 ),
            player.location.add(20, 16, 8)
        )

        pool.on<PlayerEnterBoundEvent> {
            enterCount.countDown()
        }
        pool.on<PlayerLeaveBoundEvent> {
            leaveCount.countDown()
        }

        player.teleport(player.location.add(8,0,0))
        assertTrue(enterCount.await(5L, TimeUnit.SECONDS), "PlayerEnterBoundEvent didn't work")

        player.teleport(player.location.subtract(8, 0, 0))
        assertTrue(leaveCount.await(5L, TimeUnit.SECONDS), "PlayerLeaveBoundEvent didn't work")

        pool.dispose()
        bound.dispose()
    }
}