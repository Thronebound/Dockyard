package gg.thronebound.dockyard.utils

import gg.thronebound.dockyard.player.Player

fun isDoubleInteract(player: Player): Boolean {
    return System.currentTimeMillis() - player.lastInteractionTime <= 5
}
