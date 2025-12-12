package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.resourcepack.ResourcePack

class ClientboundPlayAddResourcepackPacket(resourcepack: ResourcePack) : ClientboundPacket() {

    init {
        ResourcePack.STREAM_CODEC.write(buffer, resourcepack)
    }
}