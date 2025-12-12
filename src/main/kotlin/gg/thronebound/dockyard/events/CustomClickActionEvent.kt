package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player
import net.kyori.adventure.nbt.BinaryTag

@EventDocumentation("when custom click action is received from client")
data class CustomClickActionEvent(val player: Player, val id: String, val payload: BinaryTag?, override val context: Event.Context) : Event