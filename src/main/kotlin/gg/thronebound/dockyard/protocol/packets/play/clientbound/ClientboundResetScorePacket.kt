package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundResetScorePacket(name: String, objective: String) : ClientboundPacket() {

    init {
        buffer.writeString(name)
        buffer.writeBoolean(true)
        buffer.writeString(objective)
    }

}