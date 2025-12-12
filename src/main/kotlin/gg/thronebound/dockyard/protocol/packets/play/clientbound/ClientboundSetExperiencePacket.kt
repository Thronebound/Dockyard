package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundSetExperiencePacket(bar: Float, level: Int) : ClientboundPacket() {

    init {
        buffer.writeFloat(bar)
        buffer.writeVarInt(level)
        buffer.writeVarInt(0)
    }

}