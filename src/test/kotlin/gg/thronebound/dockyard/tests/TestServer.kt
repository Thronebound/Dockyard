package gg.thronebound.dockyard.tests

import gg.thronebound.dockyard.DockyardServer
import gg.thronebound.dockyard.registry.Biomes
import gg.thronebound.dockyard.registry.DimensionTypes
import gg.thronebound.dockyard.world.World
import gg.thronebound.dockyard.world.WorldManager
import gg.thronebound.dockyard.world.generators.VoidWorldGenerator
import org.junit.jupiter.api.BeforeAll
import java.util.concurrent.CountDownLatch

object TestServer {

    private var server: DockyardServer? = null
    lateinit var testWorld: World

    fun getServer(): DockyardServer {
        return server ?: throw IllegalStateException("Server is null")
    }

    fun getOrSetupServer(): DockyardServer {
        if (server == null) beforeAll()
        return getServer()
    }

    @BeforeAll
    @JvmStatic
    fun beforeAll() {
        server = DockyardServer {
            withIp("0.0.0.0")
            withPort(25576)
            withNetworkCompressionThreshold(-1)
            useMojangAuth(false)
            withUpdateChecker(false)
            withImplementations { spark = false }
            useDebugMode(true)
        }
        server!!.start()

        val mainWorldCountdownLatch = CountDownLatch(1)
        val secondWorldCountDownLatch = CountDownLatch(1)

        testWorld = WorldManager.create("test", VoidWorldGenerator(Biomes.THE_VOID), DimensionTypes.OVERWORLD)

        testWorld.schedule { world ->
            secondWorldCountDownLatch.countDown()
        }

        WorldManager.mainWorld.schedule { world ->
            mainWorldCountdownLatch.countDown()
        }

        mainWorldCountdownLatch.await()
        secondWorldCountDownLatch.await()
    }
}

