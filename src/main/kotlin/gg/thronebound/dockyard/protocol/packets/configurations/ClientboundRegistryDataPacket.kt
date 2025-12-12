package gg.thronebound.dockyard.protocol.packets.configurations

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.registry.Registry

class ClientboundRegistryDataPacket(val registry: Registry<*>) : ClientboundPacket() {

    init {
        registry.write(buffer)
    }
}

