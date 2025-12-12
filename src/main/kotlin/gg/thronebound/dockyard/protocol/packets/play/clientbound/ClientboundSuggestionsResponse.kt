package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundSuggestionsResponse(transactionId: Int, start: Int, length: Int, suggestions: List<String>) : ClientboundPacket() {

    init {
        buffer.writeVarInt(transactionId)
        buffer.writeVarInt(start)
        buffer.writeVarInt(length)
        buffer.writeVarInt(suggestions.size)
        suggestions.forEach {
            buffer.writeString(it)
            buffer.writeBoolean(false)
        }
    }

}