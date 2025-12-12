package gg.thronebound.dockyard.extentions

import gg.thronebound.dockyard.world.World


fun World.sendMessage(message: String, isSystem: Boolean = false) {
    players.sendMessage(message, isSystem)
}

fun World.sendActionBar(message: String) {
    players.sendActionBar(message)
}