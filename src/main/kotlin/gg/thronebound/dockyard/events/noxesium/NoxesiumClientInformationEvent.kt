package gg.thronebound.dockyard.events.noxesium

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.events.Event
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when server receives client information packet from noxesium")
class NoxesiumClientInformationEvent(val player: Player, val protocolVersion: Int, val versionString: String, override val context: Event.Context) : Event