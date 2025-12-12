package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.math.vectors.Vector3f

@EventDocumentation("when player interacts with entity. Provides XYZ of click point unlike normal `PlayerInteractWithEntityEvent`")
data class PlayerInteractAtEntityEvent(var player: Player, var entity: Entity, var clickPoint: Vector3f, var interactionHand: PlayerHand, override val context: Event.Context): CancellableEvent()