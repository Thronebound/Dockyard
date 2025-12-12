package gg.thronebound.dockyard.tests.chunk

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.world.WorldManager
import gg.thronebound.dockyard.world.chunk.ChunkPos
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class ChunkTest {

    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @AfterTest
    fun cleanup() {
        PlayerTestUtil.getOrCreateFakePlayer()
    }

    @Test
    fun testSettingBlockInNotExistingChunk() {
        val world = WorldManager.mainWorld
        assertThrows<IllegalStateException> { world.setBlock(999, 0, 999, Blocks.STONE) }
        world.getOrGenerateChunk(ChunkPos.fromLocation(world.locationAt(999, 9, 999)))
        assertDoesNotThrow { world.setBlock(999, 0, 999, Blocks.STONE) }
    }
}