package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.world.World
import gg.thronebound.dockyard.world.block.Block

@EventDocumentation("when player finished placing block. This event is mainly used for testing")
data class PlayerFinishPlacingBlockEvent(val player: Player, val world: World, val block: Block, val location: Location, override val context: Event.Context): Event