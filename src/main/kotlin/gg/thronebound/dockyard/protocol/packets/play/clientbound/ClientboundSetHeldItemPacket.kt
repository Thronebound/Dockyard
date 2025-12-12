package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundSetHeldItemPacket(slot: Int) : ClientboundPacket() {

    init {
        buffer.writeByte(slot)
    }

}