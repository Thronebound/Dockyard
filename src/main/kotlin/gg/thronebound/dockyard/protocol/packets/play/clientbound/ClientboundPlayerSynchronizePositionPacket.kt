package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.math.vectors.Vector3d
import java.util.concurrent.atomic.AtomicInteger

class ClientboundPlayerSynchronizePositionPacket(location: Location) : ClientboundPacket() {

    companion object {
        val teleportId = AtomicInteger()
    }

    init {
        buffer.writeVarInt(teleportId.incrementAndGet())
        location.toVector3d().write(buffer)
        Vector3d().write(buffer)
        buffer.writeFloat(location.yaw)
        buffer.writeFloat(location.pitch)
        buffer.writeInt(0)
    }
}