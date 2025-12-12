package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestFor
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.data.components.ConsumableComponent
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerCancelledConsumingEvent
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundSetPlayerHeldItemPacket
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundUseItemPacket
import gg.thronebound.dockyard.registry.Items
import gg.thronebound.dockyard.scheduler.runLaterAsync
import gg.thronebound.dockyard.scheduler.runnables.ticks
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@TestFor(PlayerCancelledConsumingEvent::class)
class PlayerCancelConsumingEventTest {

    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<PlayerCancelledConsumingEvent> {
            count.countDown()
        }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        player.setHeldItem(PlayerHand.MAIN_HAND, Items.APPLE.toItemStack().withConsumable(1f, ConsumableComponent.Animation.EAT))

        PlayerTestUtil.sendPacket(player, ServerboundUseItemPacket(PlayerHand.MAIN_HAND, 0, 0f, 0f))
        runLaterAsync(5.ticks) {
            PlayerTestUtil.sendPacket(player, ServerboundSetPlayerHeldItemPacket(9))
        }

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
        PlayerTestUtil.sendPacket(player, ServerboundSetPlayerHeldItemPacket(0))
    }
}