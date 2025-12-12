package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player picks item from an entity")
data class PlayerPickItemFromEntityEvent(val player: Player, var entity: Entity, val includeData: Boolean, override val context: Event.Context): Event