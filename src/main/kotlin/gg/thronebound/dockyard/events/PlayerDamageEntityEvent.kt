package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player attacks another entity")
data class PlayerDamageEntityEvent(var player: Player, var entity: Entity, override val context: Event.Context) : CancellableEvent()