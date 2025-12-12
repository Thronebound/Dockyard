package gg.thronebound.dockyard.protocol.packets.configurations

import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.server.FeatureFlags

class ClientboundFeatureFlagsPacket(flags: MutableList<FeatureFlags.Flag>) : ClientboundPacket() {

    init {
        buffer.writeVarInt(flags.size)
        flags.forEach {
            buffer.writeString(it.identifier)
        }
    }
}