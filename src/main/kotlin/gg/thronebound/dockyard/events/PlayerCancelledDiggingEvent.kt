package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.world.block.Block

@EventDocumentation("when player cancels digging of a block")
data class PlayerCancelledDiggingEvent(val player: Player, val location: Location, val block: Block, override val context: Event.Context) : Event