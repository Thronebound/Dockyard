package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player steers a vehicle")
data class PlayerSteerVehicleEvent(val player: Player, val vehicle: Entity, val location: Location, override val context: Event.Context) : Event