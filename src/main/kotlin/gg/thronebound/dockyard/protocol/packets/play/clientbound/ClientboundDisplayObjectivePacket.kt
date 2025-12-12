package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundDisplayObjectivePacket(position: ObjectivePosition, text: String) : ClientboundPacket() {

    init {
        buffer.writeVarInt(position.ordinal)
        buffer.writeString(text)
    }

}

enum class ObjectivePosition {
    LIST,
    SIDEBAR,
    BELOW_NAME
}