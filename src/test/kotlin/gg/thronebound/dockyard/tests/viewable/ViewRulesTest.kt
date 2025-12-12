package gg.thronebound.dockyard.tests.viewable

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.entity.EntityManager.despawnEntity
import gg.thronebound.dockyard.entity.EntityManager.spawnEntity
import gg.thronebound.dockyard.entity.Parrot
import gg.thronebound.dockyard.world.WorldManager
import kotlin.test.BeforeTest
import kotlin.test.Test

class ViewRulesTest {

    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testViewRules() {
        val player = PlayerTestUtil.getOrCreateFakePlayer()
        val location = WorldManager.mainWorld.defaultSpawnLocation

        val entity = location.world.spawnEntity(Parrot(location))

        entity.addViewRule("test") { testPlayer ->
            testPlayer.experienceLevel.value >= 5
        }

        player.teleport(location)
        player.entityViewSystem.tick()

        assert(!entity.viewers.contains(player))

        player.experienceLevel.value = 10
        player.entityViewSystem.tick()

        assert(entity.viewers.contains(player))

        player.experienceLevel.value = 0
        player.entityViewSystem.tick()

        assert(!entity.viewers.contains(player))

        location.world.despawnEntity(entity)
    }
}