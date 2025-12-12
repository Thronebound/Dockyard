package gg.thronebound.dockyard.protocol.packets.configurations

import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundKnownPacksPackets(knowPackets: MutableList<KnownPack>): ClientboundPacket() {

    init {
        buffer.writeVarInt(knowPackets.size)
        knowPackets.forEach {
            buffer.writeString(it.namespace)
            buffer.writeString(it.id)
            buffer.writeString(it.version)
        }
    }
}

data class KnownPack(
    val namespace: String,
    val id: String,
    val version: String
)