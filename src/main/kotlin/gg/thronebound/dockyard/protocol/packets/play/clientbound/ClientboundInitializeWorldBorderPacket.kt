package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.extentions.writeVarLong
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundInitializeWorldBorderPacket(
    oldDiameter: Double,
    newDiameter: Double,
    speed: Long,
    warningBlocks: Int,
    warningTime: Int,
) : ClientboundPacket() {

    init {
        buffer.writeDouble(10.0)
        buffer.writeDouble(10.0)
        buffer.writeDouble(oldDiameter)
        buffer.writeDouble(newDiameter)

        buffer.writeVarLong(speed)

        buffer.writeVarInt(10)
        buffer.writeVarInt(warningBlocks)
        buffer.writeVarInt(warningTime)
    }
}