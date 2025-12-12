package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.scheduler.runnables.inWholeMinecraftTicks
import kotlin.time.Duration

data class SetItemCooldownPacket(var group: String, var cooldown: Duration): ClientboundPacket() {

    init {
        buffer.writeString(group)
        buffer.writeVarInt(cooldown.inWholeMinecraftTicks)
    }

}