package gg.thronebound.dockyard.protocol.packets.handshake

import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundStatusResponsePacket(statusJson: String): ClientboundPacket() {

    init {
        buffer.writeString(statusJson)
    }
}