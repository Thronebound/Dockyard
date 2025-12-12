package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.world.chunk.ChunkPos

@EventDocumentation("when player enters new chunk")
data class PlayerEnterChunkEvent(val oldChunkPos: ChunkPos, val newChunkPos: ChunkPos?, val player: Player, override val context: Event.Context) : Event