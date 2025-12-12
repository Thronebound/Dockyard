package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundClearTitlePacket(val reset: Boolean) : ClientboundPacket() {
    init {
        buffer.writeBoolean(reset)
    }
}