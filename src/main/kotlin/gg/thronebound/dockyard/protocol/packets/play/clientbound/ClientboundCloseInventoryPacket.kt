package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundCloseInventoryPacket(id: Int) : ClientboundPacket() {
    init {
        buffer.writeByte(id)
    }
}