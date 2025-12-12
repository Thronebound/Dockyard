package gg.thronebound.dockyard.tests.location

import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.world.WorldManager
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class HighestLowestPointTest {

    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testLowestPoint() {
        val world = WorldManager.mainWorld

        world.setBlock(0, 0, 0, Blocks.STONE)
        val zeroZeroZero = Location(0, 0, 0, world)

        assertEquals(zeroZeroZero, zeroZeroZero.closestNonAirBelow?.second)

        world.setBlock(0, 0, 0, Blocks.AIR)
        world.setBlock(0, -1, 0, Blocks.AIR)
        world.setBlock(0, -30, 0, Blocks.STONE)

        assertEquals(world.locationAt(0, -30, 0), zeroZeroZero.closestNonAirBelow?.second)

        world.setBlock(0, -30, 0, Blocks.AIR)

        assertEquals(null, zeroZeroZero.closestNonAirBelow)

        world.setBlock(0, -30, 0, Blocks.SHORT_GRASS)

        assertEquals(world.locationAt(0, -30, 0), zeroZeroZero.closestNonAirBelow?.second)
        assertEquals(null, zeroZeroZero.closestSolidBelow)

        world.setBlock(0, -30, 0, Blocks.AIR)
    }

    @Test
    fun testHighestPoint() {
        val world = WorldManager.mainWorld

        world.setBlock(0, 0, 0, Blocks.STONE)
        world.setBlock(0, 1, 0, Blocks.AIR)
        val zeroZeroZero = Location(0, 0, 0, world)

        assertEquals(zeroZeroZero, zeroZeroZero.closestNonAirAbove?.second)

        world.setBlock(0, 0, 0, Blocks.AIR)
        world.setBlock(0, 30, 0, Blocks.STONE)

        assertEquals(world.locationAt(0, 30, 0), zeroZeroZero.closestNonAirAbove?.second)

        world.setBlock(0, 30, 0, Blocks.AIR)

        assertEquals(null, zeroZeroZero.closestNonAirAbove)

        world.setBlock(0, 30, 0, Blocks.SHORT_GRASS)

        assertEquals(world.locationAt(0, 30, 0), zeroZeroZero.closestNonAirAbove?.second)
        assertEquals(null, zeroZeroZero.closestSolidAbove)

        world.setBlock(0, 30, 0, Blocks.AIR)
    }
}