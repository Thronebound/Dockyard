package gg.thronebound.dockyard.events.noxesium

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.events.Event
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when noxesium sends riptide packet")
data class NoxesiumRiptideEvent(val player: Player, val slot: Int, override val context: Event.Context) : Event