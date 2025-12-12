package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.protocol.writeOptional
import io.netty.buffer.ByteBuf

class ClientboundSelectAdvancementsTabPacket(val identifier: String?) : ClientboundPacket() {

    init {
        buffer.writeOptional(identifier, ByteBuf::writeString)
    }

}