package gg.thronebound.dockyard.events.noxesium

import com.noxcrew.noxesium.api.protocol.ClientSettings
import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.events.Event
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when server receives client settings packet from noxesium")
data class NoxesiumClientSettingsEvent(val player: Player, val clientSettings: ClientSettings, override val context: Event.Context) : Event