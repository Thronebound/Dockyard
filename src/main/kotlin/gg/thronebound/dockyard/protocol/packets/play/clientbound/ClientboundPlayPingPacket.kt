package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundPlayPingPacket(time: Int): ClientboundPacket() {

    init {
        buffer.writeInt(time)
    }
}