package gg.thronebound.dockyard.tests.npc

import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.tests.utils.waitUntilFuture
import gg.thronebound.dockyard.entity.EntityManager.despawnEntity
import gg.thronebound.dockyard.entity.EntityManager.spawnEntity
import gg.thronebound.dockyard.npc.FakePlayer
import gg.thronebound.dockyard.world.WorldManager
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class FakePlayerTest {

    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testTeam() {
        val fakePlayer = WorldManager.mainWorld.spawnEntity<FakePlayer>(FakePlayer(WorldManager.mainWorld.defaultSpawnLocation))

        assertEquals(fakePlayer.team.value, fakePlayer.npcTeam)
    }

    @Test
    fun testSkin() {
        val fakePlayer = WorldManager.mainWorld.spawnEntity<FakePlayer>(FakePlayer(WorldManager.mainWorld.defaultSpawnLocation))

        assertEquals(true, fakePlayer.gameProfile.properties.isEmpty())

        waitUntilFuture(fakePlayer.setSkinFromUsername("LukynkaCZE"))

        assertEquals(false, fakePlayer.gameProfile.properties.isEmpty())
        assertNotNull(fakePlayer.gameProfile.properties.firstOrNull { property -> property.name == "textures" })

        fakePlayer.skin.value = null

        assertEquals(true, fakePlayer.gameProfile.properties.isEmpty())

        WorldManager.mainWorld.despawnEntity(fakePlayer)
    }
}