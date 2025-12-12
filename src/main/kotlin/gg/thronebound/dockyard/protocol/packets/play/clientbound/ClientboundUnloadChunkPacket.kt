package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.world.chunk.ChunkPos

class ClientboundUnloadChunkPacket(chunkPos: ChunkPos): ClientboundPacket() {

    init {
        buffer.writeInt(chunkPos.z)
        buffer.writeInt(chunkPos.x)
    }

}