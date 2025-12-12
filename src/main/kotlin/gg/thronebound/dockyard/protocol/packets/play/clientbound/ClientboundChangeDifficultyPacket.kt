package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.world.Difficulty

class ClientboundChangeDifficultyPacket(difficulty: Difficulty, locked: Boolean = false) : ClientboundPacket() {

    init {
        buffer.writeByte(difficulty.ordinal)
        buffer.writeBoolean(locked)
    }
}