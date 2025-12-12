package gg.thronebound.dockyard.events.noxesium

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.events.Event
import gg.thronebound.dockyard.noxesium.protocol.serverbound.ServerboundNoxesiumQibTriggeredPacket
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when server receives qib trigger packet from noxesium")
data class NoxesiumQibTriggeredEvent(val player: Player, val behaviour: String, val qibType: ServerboundNoxesiumQibTriggeredPacket.Type, val entityId: Int, override val context: Event.Context) : Event