package gg.thronebound.dockyard.protocol.packets.configurations

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.resourcepack.ResourcePack

data class ClientboundConfigurationAddResourcePackPacket(val resourcePack: ResourcePack) : ClientboundPacket() {

    init {
        ResourcePack.STREAM_CODEC.write(buffer, resourcePack)
    }
}