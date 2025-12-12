package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.PlayerHand

@EventDocumentation("when player interacts with an entity")
data class PlayerInteractWithEntityEvent(var player: Player, var entity: Entity, var interactionHand: PlayerHand, override val context: Event.Context): CancellableEvent()
