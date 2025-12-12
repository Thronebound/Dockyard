package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player sends a chat message")
data class PlayerChatMessageEvent(var message: String, val player: Player, override val context: Event.Context) : CancellableEvent()