package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeTextComponent
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundScoreboardObjectivePacket(name: String, mode: ScoreboardMode, value: String?, type: ScoreboardType?): ClientboundPacket() {

    init {
        buffer.writeString(name)
        buffer.writeByte(mode.ordinal)
        if(mode == ScoreboardMode.CREATE || mode == ScoreboardMode.EDIT_TEXT) {
            if(value == null) throw Exception("value needs to be not null when using CREATE or EDIT_TEXT mode!")
            if(type == null) throw Exception("type needs to be not null when using CREATE or EDIT_TEXT mode!")
            buffer.writeTextComponent(value)
            buffer.writeEnum<ScoreboardType>(type)
            buffer.writeBoolean(true)
            buffer.writeVarInt(0)
        }
    }
}


enum class ScoreboardMode {
    CREATE,
    REMOVE,
    EDIT_TEXT
}

enum class ScoreboardType {
    INTEGER,
    HEARTS
}