package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.resourcepack.ResourcePack
import io.github.dockyardmc.tide.stream.StreamCodec
import java.util.*

data class ClientboundPlayRemoveResourcepackPacket(val uuid: UUID?) : ClientboundPacket() {

    constructor(resourcePack: ResourcePack) : this(resourcePack.uuid)

    init {
        StreamCodec.UUID.optional().write(buffer, uuid)
    }
}