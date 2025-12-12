package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.world.World

@EventDocumentation("when player changes worlds")
data class PlayerChangeWorldEvent(val player: Player, val oldWorld: World, val newWorld: World, override val context: Event.Context) : Event