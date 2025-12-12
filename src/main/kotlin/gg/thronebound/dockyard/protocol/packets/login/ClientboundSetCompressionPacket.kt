package gg.thronebound.dockyard.protocol.packets.login

import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundSetCompressionPacket(compression: Int) : ClientboundPacket() {

    init {
        buffer.writeVarInt(compression)
    }

}