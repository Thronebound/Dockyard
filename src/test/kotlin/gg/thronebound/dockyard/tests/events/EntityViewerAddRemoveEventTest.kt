package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestFor
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.entity.EntityManager
import gg.thronebound.dockyard.entity.Parrot
import gg.thronebound.dockyard.events.EntityViewerAddEvent
import gg.thronebound.dockyard.events.EntityViewerRemoveEvent
import gg.thronebound.dockyard.events.EventPool
import org.junit.jupiter.api.Assertions.assertTrue
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test

@TestFor(EntityViewerAddEvent::class, EntityViewerRemoveEvent::class)
class EntityViewerAddRemoveEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val addCount = CountDownLatch(1)
        val removeCount = CountDownLatch(1)

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        val mob = EntityManager.spawnEntity(Parrot(player.world.locationAt(0,0,0)))

        pool.on<EntityViewerAddEvent> {
            addCount.countDown()
        }
        pool.on<EntityViewerRemoveEvent> {
            removeCount.countDown()
        }

        mob.addViewer(player)
        assertTrue(addCount.await(5L, TimeUnit.SECONDS))

        mob.removeViewer(player)
        assertTrue(removeCount.await(5L, TimeUnit.SECONDS))

        pool.dispose()
        mob.dispose()
    }
}
