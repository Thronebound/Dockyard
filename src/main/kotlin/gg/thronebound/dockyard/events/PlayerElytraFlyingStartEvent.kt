package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player starts flying with elytra (not flight state change)")
data class PlayerElytraFlyingStartEvent(val player: Player, override val context: Event.Context) : Event