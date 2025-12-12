package gg.thronebound.dockyard.utils.viewable

import gg.thronebound.dockyard.player.Player

data class ViewRule(private val filter: (Player) -> Boolean) {

    fun passes(player: Player): Boolean {
        return filter.invoke(player)
    }

}