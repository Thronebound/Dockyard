package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when server sends player a chat message")
data class ServerSendPlayerMessageEvent(val player: Player, val message: String, val isSystem: Boolean, override val context: Event.Context) : CancellableEvent()