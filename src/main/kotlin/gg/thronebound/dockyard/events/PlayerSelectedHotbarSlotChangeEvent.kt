package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player


@EventDocumentation("when player changes their held slot")
data class PlayerSelectedHotbarSlotChangeEvent(val player: Player, val slot: Int, override val context: Event.Context) : CancellableEvent()