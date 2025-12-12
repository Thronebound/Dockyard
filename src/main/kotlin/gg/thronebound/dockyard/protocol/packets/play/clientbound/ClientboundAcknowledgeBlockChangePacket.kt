package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundAcknowledgeBlockChangePacket(val sequenceId: Int) : ClientboundPacket() {

    init {
        buffer.writeVarInt(sequenceId)
    }

}