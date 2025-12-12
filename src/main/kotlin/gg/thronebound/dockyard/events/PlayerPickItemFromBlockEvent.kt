package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when player picks a item from block")
data class PlayerPickItemFromBlockEvent(
    val player: Player,
    val blockLocation: Location,
    var block: gg.thronebound.dockyard.world.block.Block,
    val includeData: Boolean,
    override val context: Event.Context
) : CancellableEvent()