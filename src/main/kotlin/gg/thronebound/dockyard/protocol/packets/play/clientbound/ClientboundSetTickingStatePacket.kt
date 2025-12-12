package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundSetTickingStatePacket(
    tickRate: Int,
    isFrozen: Boolean,
) : ClientboundPacket() {

    init {
        buffer.writeFloat(tickRate.toFloat())
        buffer.writeBoolean(isFrozen)
    }

}