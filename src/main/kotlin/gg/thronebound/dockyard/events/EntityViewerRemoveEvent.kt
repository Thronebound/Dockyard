package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when viewer is removed from entity viewer list")
class EntityViewerRemoveEvent(var entity: Entity, var viewer: Player, override val context: Event.Context) : CancellableEvent()
