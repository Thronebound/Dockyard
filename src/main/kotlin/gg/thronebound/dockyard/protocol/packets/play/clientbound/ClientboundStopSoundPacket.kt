package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeByte
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundStopSoundPacket(flags: Byte, category: SoundCategory?, sound: String?): ClientboundPacket() {

    init {
        buffer.writeByte(flags)
        if((flags == 3.toByte() || flags == 1.toByte()) && category != null) {
            buffer.writeVarInt(category.ordinal)
        }
        if((flags == 2.toByte() || flags == 3.toByte()) && sound != null) {
            buffer.writeString(sound)
        }
    }

}