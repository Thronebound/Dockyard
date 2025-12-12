package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundSetHealthPacket(var health: Float, var food: Int, var saturation: Float) : ClientboundPacket() {

    init {
        buffer.writeFloat(health)
        buffer.writeVarInt(food)
        buffer.writeFloat(saturation)
    }
}