package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerEnterChunkEvent
import gg.thronebound.dockyard.world.chunk.ChunkPos
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerEnterChunkEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.teleport(player.world.locationAt(0, 0, 0))

        val location = player.location.add(16, 0, 0)

        pool.on<PlayerEnterChunkEvent> {
            if (it.newChunkPos == ChunkPos.fromLocation(location)) {
                count.countDown()
            }
        }

        player.teleport(location)

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}