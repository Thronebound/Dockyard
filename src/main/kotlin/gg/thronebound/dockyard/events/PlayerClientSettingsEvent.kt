package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.types.ClientSettings

@EventDocumentation("server receives information about client's settings")
data class PlayerClientSettingsEvent(var clientSettings: ClientSettings, var player: Player, override val context: Event.Context) : Event