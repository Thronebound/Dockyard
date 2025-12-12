package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player uses movement input")
data class PlayerInputEvent(
    val player: Player,
    val forward: Boolean,
    val backward: Boolean,
    val left: Boolean,
    val right: Boolean,
    val jump: Boolean,
    val shift: Boolean,
    val sprint: Boolean,
    override val context: Event.Context,
): Event