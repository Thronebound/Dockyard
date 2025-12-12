package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.protocol.types.writeList
import gg.thronebound.dockyard.apis.serverlinks.ServerLink

class ClientboundServerLinksPacket(serverLinks: Collection<ServerLink>) : ClientboundPacket() {

    init {
        buffer.writeList(serverLinks, ServerLink::write)
    }
}

