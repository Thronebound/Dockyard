package gg.thronebound.dockyard.protocol.packets.login

import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import io.github.dockyardmc.scroll.extensions.toComponent

class ClientboundLoginDisconnectPacket(reason: String): ClientboundPacket() {

    init {
        buffer.writeString(reason.toComponent().toJson())
    }
}