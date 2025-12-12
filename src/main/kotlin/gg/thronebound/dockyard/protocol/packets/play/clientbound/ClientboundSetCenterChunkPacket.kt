package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.world.chunk.ChunkPos

class ClientboundSetCenterChunkPacket(chunkPos: ChunkPos): ClientboundPacket() {

    init {
        buffer.writeVarInt(chunkPos.x)
        buffer.writeVarInt(chunkPos.z)
    }

}