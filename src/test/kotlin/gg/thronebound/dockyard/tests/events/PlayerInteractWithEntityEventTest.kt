package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.entity.EntityManager
import gg.thronebound.dockyard.entity.Parrot
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerInteractWithEntityEvent
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.protocol.packets.play.serverbound.EntityInteractionType
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundEntityInteractPacket
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerInteractWithEntityEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<PlayerInteractWithEntityEvent> { count.countDown() }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        val entity = EntityManager.spawnEntity(Parrot(player.location.add(1, 2, 0)))

        PlayerTestUtil.sendPacket(
            player,
            ServerboundEntityInteractPacket(
                entity,
                EntityInteractionType.INTERACT,
                hand = PlayerHand.MAIN_HAND,
                sneaking = false
            )
        )

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
        entity.dispose()
    }
}