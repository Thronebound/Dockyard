package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player opens vehicle's inventory while riding it")
data class PlayerVehicleInventoryOpenEvent(val player: Player, override val context: Event.Context) : CancellableEvent()