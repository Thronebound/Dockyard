package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundPlayPingResponsePacket(payload: Long): ClientboundPacket() {

    init {
        buffer.writeLong(payload)
    }

}