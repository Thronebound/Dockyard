package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.entity.EntityManager
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.PlayerConnectEvent
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.PlayerManager
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.world.WorldManager
import io.netty.channel.ChannelHandlerContext
import org.mockito.Mockito
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PlayerConnectEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<PlayerConnectEvent> { count.countDown() }

        val player = Player(
            "LukynkaCZE_2",
            id = EntityManager.entityIdCounter.incrementAndGet(),
            uuid = UUID.randomUUID(),
            world = WorldManager.mainWorld,
            connection = Mockito.mock<ChannelHandlerContext>(),
            address = "0.0.0.0",
            networkManager = PlayerNetworkManager(),
        )
        PlayerManager.add(player, player.networkManager)
        player.world.join(player)

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()

        PlayerManager.remove(player)
    }
}