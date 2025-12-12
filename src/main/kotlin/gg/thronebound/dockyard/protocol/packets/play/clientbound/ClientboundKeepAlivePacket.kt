package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundKeepAlivePacket(keepAliveId: Long) : ClientboundPacket() {

    init {
        buffer.writeLong(keepAliveId)
    }

}