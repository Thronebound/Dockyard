package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.world.World

@EventDocumentation("when player is in configuration phase and needs initial world to spawn in")
data class PlayerSpawnEvent(val player: Player, var world: World, override val context: Event.Context) : Event