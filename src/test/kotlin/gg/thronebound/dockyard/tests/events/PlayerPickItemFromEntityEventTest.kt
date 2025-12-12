package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.entity.EntityManager
import gg.thronebound.dockyard.entity.Parrot
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerPickItemFromEntityEvent
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundPickItemFromEntityPacket
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerPickItemFromEntityEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<PlayerPickItemFromEntityEvent> {
            count.countDown()
        }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        val entity = EntityManager.spawnEntity(Parrot(player.location.add(0,1,0)))
        PlayerTestUtil.sendPacket(ServerboundPickItemFromEntityPacket(entity.id, false))

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
        entity.dispose()
    }
}