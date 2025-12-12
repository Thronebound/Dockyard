package gg.thronebound.dockyard.protocol.packets.configurations

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.resourcepack.ResourcePack
import io.github.dockyardmc.tide.stream.StreamCodec
import java.util.*

data class ClientboundConfigurationRemoveResourcePackPacket(val uuid: UUID? = null) : ClientboundPacket() {

    constructor(resourcePack: ResourcePack) : this(resourcePack.uuid)

    init {
        StreamCodec.UUID.optional().write(buffer, uuid)
    }
}