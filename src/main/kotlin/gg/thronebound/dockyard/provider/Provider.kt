package gg.thronebound.dockyard.provider

import gg.thronebound.dockyard.player.Player

interface Provider {
    val playerGetter: Collection<Player>
}