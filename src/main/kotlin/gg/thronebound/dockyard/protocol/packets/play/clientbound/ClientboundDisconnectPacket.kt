package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeTextComponent
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundDisconnectPacket(reason: String) : ClientboundPacket() {

    init {
        buffer.writeTextComponent(reason)
    }

}