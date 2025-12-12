package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.sounds.Sound

class ClientboundPlaySoundPacket(sound: Sound, location: Location) : ClientboundPacket() {

    init {
        buffer.writeVarInt(0)
        buffer.writeString(sound.identifier)
        buffer.writeBoolean(false)
        buffer.writeEnum<SoundCategory>(sound.category)
        buffer.writeInt((location.x * 8.0).toInt())
        buffer.writeInt((location.y * 8.0).toInt())
        buffer.writeInt((location.z * 8.0).toInt())
        buffer.writeFloat(sound.volume)
        buffer.writeFloat(sound.pitch)
        buffer.writeLong(sound.seed)
    }
}

enum class SoundCategory {
    MASTER,
    MUSIC,
    RECORDS,
    WEATHER,
    BLOCKS,
    HOSTILE,
    NEUTRAL,
    PLAYERS,
    AMBIENT,
    VOICE,
    UI;
}