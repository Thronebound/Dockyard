package gg.thronebound.dockyard.tests.schematic

import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.schematics.SchematicReader
import gg.thronebound.dockyard.utils.Resources
import gg.thronebound.dockyard.world.WorldManager
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SchematicTest {

    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testSchematicReadingAndPlacing() {
        val world = WorldManager.mainWorld
        assertDoesNotThrow {
            val schematic = SchematicReader.read(Resources.getFile("test.schem").readBytes())
            world.placeSchematic(schematic, world.locationAt(0, 0, 0))
        }
        assertEquals(Blocks.RED_WOOL, world.locationAt(0, 0, 0).block.registryBlock)
        assertEquals(Blocks.NETHER_BRICK_FENCE, world.locationAt(4, 1, 5).block.registryBlock)
        assertEquals(Blocks.STONE, world.locationAt(27, 1, 23).block.registryBlock)


        assertEquals(Blocks.SCULK_SENSOR, world.locationAt(14, 1, 7).block.registryBlock)
        assertNotNull(world.getBlockEntityDataOrNull(14, 1, 7))
    }
}