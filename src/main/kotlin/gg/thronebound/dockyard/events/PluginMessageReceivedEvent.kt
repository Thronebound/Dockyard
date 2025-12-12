package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.plugin.messages.PluginMessage

@EventDocumentation("server receives plugin message from client (Custom Payload Packet)")
data class PluginMessageReceivedEvent(val player: Player, val contents: PluginMessage.Contents, override val context: Event.Context) : CancellableEvent()