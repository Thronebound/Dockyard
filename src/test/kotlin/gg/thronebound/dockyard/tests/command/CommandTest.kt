package gg.thronebound.dockyard.tests.command

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.player.Player

abstract class CommandTest {

    inline fun run(command: String, assert: (Player) -> Unit) {
        PlayerTestUtil.getOrCreateFakePlayer().runCommand(command)
        assert.invoke(PlayerTestUtil.getOrCreateFakePlayer())
    }

}