package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.sounds.Sound

class ClientboundEntityPlaySoundPacket(sound: Sound, source: Entity): ClientboundPacket() {

    init {
        buffer.writeVarInt(0)
        buffer.writeString(sound.identifier)
        buffer.writeBoolean(false)
        buffer.writeEnum<SoundCategory>(sound.category)
        buffer.writeVarInt(source.id)
        buffer.writeFloat(sound.volume)
        buffer.writeFloat(sound.pitch)
        buffer.writeLong(sound.seed)
    }
}