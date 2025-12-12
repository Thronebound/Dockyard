package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeTextComponent
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundUpdateScorePacket(objective: String, line: Int, text: String) : ClientboundPacket() {
    init {
        buffer.writeString("line-$line")
        buffer.writeString(objective)
        buffer.writeVarInt(line)
        buffer.writeBoolean(true)
        buffer.writeTextComponent(text)
        buffer.writeBoolean(true)
        buffer.writeVarInt(0)
    }
}