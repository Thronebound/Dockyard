package gg.thronebound.dockyard.tests.block.handlers

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.inventory.clearInventory
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.registry.Items
import gg.thronebound.dockyard.math.vectors.Vector3
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.world.WorldManager
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class SlabBlockHandlerTest {

    @BeforeTest
    fun before() {
        TestServer.getOrSetupServer()
        PlayerTestUtil.getOrCreateFakePlayer().clearInventory()
        BlockHandlerTestUtil.reset()
    }

    @Test
    fun testPlace() {
        val world = WorldManager.mainWorld
        PlayerTestUtil.getOrCreateFakePlayer().teleport(Location(10, 10, 10, WorldManager.mainWorld))
        BlockHandlerTestUtil.reset()

        world.setBlock(0, -1, 0, Blocks.STONE)
        world.setBlock(0, 1, 0, Blocks.STONE)

        assertEquals(Blocks.RESIN_BRICK_SLAB.withBlockStates("type" to "bottom"), BlockHandlerTestUtil.place(Items.RESIN_BRICK_SLAB, Direction.UP, Vector3(0, -1, 0), Vector3f()))
        BlockHandlerTestUtil.reset()
        assertEquals(Blocks.RESIN_BRICK_SLAB.withBlockStates("type" to "top"), BlockHandlerTestUtil.place(Items.RESIN_BRICK_SLAB, Direction.DOWN, Vector3(0, 1, 0), Vector3f()))
        BlockHandlerTestUtil.reset()

        world.setBlock(0, 0, -1, Blocks.STONE)
        assertEquals(Blocks.RESIN_BRICK_SLAB.withBlockStates("type" to "top"), BlockHandlerTestUtil.place(Items.RESIN_BRICK_SLAB, Direction.SOUTH, Vector3(0, 0, -1), Vector3f(0f, 1f, 0f)))
        BlockHandlerTestUtil.reset()
        assertEquals(Blocks.RESIN_BRICK_SLAB.withBlockStates("type" to "bottom"), BlockHandlerTestUtil.place(Items.RESIN_BRICK_SLAB, Direction.SOUTH, Vector3(0, 0, -1), Vector3f(0f, 0f, 0f)))

        BlockHandlerTestUtil.reset()
        BlockHandlerTestUtil.place(Items.RESIN_BRICK_SLAB, Direction.SOUTH, Vector3(0, 0, -1), Vector3f(0f, 1f, 0f))
        assertEquals(Blocks.RESIN_BRICK_SLAB.withBlockStates("type" to "double"), BlockHandlerTestUtil.place(Items.RESIN_BRICK_SLAB, Direction.SOUTH, Vector3(0, 0, -1), Vector3f(0f, 0f, 0f)))

        BlockHandlerTestUtil.reset()
        BlockHandlerTestUtil.place(Items.RESIN_BRICK_SLAB, Direction.SOUTH, Vector3(0, 0, -1), Vector3f(0f, 0f, 0f))
        assertEquals(Blocks.RESIN_BRICK_SLAB.withBlockStates("type" to "double"), BlockHandlerTestUtil.place(Items.RESIN_BRICK_SLAB, Direction.SOUTH, Vector3(0, 0, -1), Vector3f(0f, 1f, 0f)))

        BlockHandlerTestUtil.reset()
        world.setBlock(0, 1, 0, Blocks.AIR)
        BlockHandlerTestUtil.place(Items.RESIN_BRICK_SLAB, Direction.SOUTH, Vector3(0, 0, -1), Vector3f(0f, 0f, 0f))
        assertEquals(Blocks.RESIN_BRICK_SLAB.withBlockStates("type" to "bottom"), BlockHandlerTestUtil.place(Items.CHERRY_SLAB, Direction.SOUTH, Vector3(0, 0, -1), Vector3f(0f, 1f, 0f)))
        assertEquals(Blocks.AIR.toBlock(), WorldManager.mainWorld.getBlock(0, 1, 0))
    }
}