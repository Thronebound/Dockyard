package gg.thronebound.dockyard.protocol.packets.handshake

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundPingResponsePacket(time: Long): ClientboundPacket() {

    init {
        buffer.writeLong(time)
    }
}