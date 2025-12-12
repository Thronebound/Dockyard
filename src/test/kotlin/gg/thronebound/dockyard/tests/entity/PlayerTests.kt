package gg.thronebound.dockyard.tests.entity

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.systems.GameMode
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerTests {

    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @AfterTest
    fun cleanup() {
    }

    @Test
    fun testAbilities() {
        val player = PlayerTestUtil.getOrCreateFakePlayer()

        player.gameMode.value = GameMode.CREATIVE
        assertAbilities(player, isInvulnerable = true, isFlying = false, canFly = true)
        player.gameMode.value = GameMode.SPECTATOR
        assertAbilities(player, isInvulnerable = true, isFlying = true, canFly = true)
        player.gameMode.value = GameMode.CREATIVE
        assertAbilities(player, isInvulnerable = true, isFlying = true, canFly = true)
        player.gameMode.value = GameMode.ADVENTURE
        assertAbilities(player, isInvulnerable = false, isFlying = false, canFly = false)
        player.gameMode.value = GameMode.SURVIVAL
        assertAbilities(player, isInvulnerable = false, isFlying = false, canFly = false)
    }

    private fun assertAbilities(player: Player, isInvulnerable: Boolean, isFlying: Boolean, canFly: Boolean) {
        assertEquals(isInvulnerable, player.isInvulnerable)
        assertEquals(isFlying, player.isFlying.value)
        assertEquals(canFly, player.canFly.value)
    }
}