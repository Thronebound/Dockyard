package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when server receives the minecraft:unregister plugin message with list of custom channels")
data class UnregisterPluginChannelsEvent(val player: Player, val channels: List<String>, override val context: Event.Context) : Event