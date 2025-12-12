package gg.thronebound.dockyard.protocol.packets.configurations

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.protocol.types.writeList
import gg.thronebound.dockyard.apis.serverlinks.ServerLink

class ClientboundConfigurationServerLinksPacket(
    serverLinks: Collection<ServerLink>
) : ClientboundPacket() {

    init {
        buffer.writeList(serverLinks, ServerLink::write)
    }
}