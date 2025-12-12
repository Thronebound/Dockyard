package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.world.block.Block

@EventDocumentation("when player breaks a block")
data class PlayerBlockBreakEvent(val player: Player, var block: Block, var location: Location, val face: Direction, override val context: Event.Context) : CancellableEvent()