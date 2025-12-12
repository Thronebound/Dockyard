package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.world.waypoint.WaypointData

data class ClientboundTrackedWaypointPacket(val operation: Operation, val waypointData: WaypointData) : ClientboundPacket() {

    enum class Operation {
        TRACK,
        UNTRACK,
        UPDATE
    }

    init {
        buffer.writeEnum(operation)
        waypointData.write(buffer)
    }

}