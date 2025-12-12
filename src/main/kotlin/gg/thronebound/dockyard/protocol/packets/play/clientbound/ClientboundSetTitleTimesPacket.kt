package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.scheduler.runnables.inWholeMinecraftTicks
import kotlin.time.Duration

data class ClientboundSetTitleTimesPacket(
    val fadeIn: Duration,
    val stay: Duration,
    val fadeOut: Duration
) : ClientboundPacket() {
    init {
        buffer.writeInt(fadeIn.inWholeMinecraftTicks)
        buffer.writeInt(stay.inWholeMinecraftTicks)
        buffer.writeInt(fadeOut.inWholeMinecraftTicks)
    }
}