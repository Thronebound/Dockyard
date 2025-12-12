package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when viewer is added to an entity viewer list")
class EntityViewerAddEvent(var entity: Entity, var viewer: Player, override val context: Event.Context) : CancellableEvent()