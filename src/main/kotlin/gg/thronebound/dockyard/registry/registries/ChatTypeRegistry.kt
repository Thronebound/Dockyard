package gg.thronebound.dockyard.registry.registries

import gg.thronebound.dockyard.protocol.packets.configurations.ClientboundRegistryDataPacket
import gg.thronebound.dockyard.registry.DynamicRegistry
import gg.thronebound.dockyard.registry.RegistryEntry

object ChatTypeRegistry : DynamicRegistry<ChatType>() {

    override val identifier: String = "minecraft:chat_type"

    override fun updateCache() {
        cachedPacket = ClientboundRegistryDataPacket(this)
    }
}

class ChatType : RegistryEntry {

    override fun getProtocolId(): Int {
        throw UnsupportedOperationException()
    }

    override fun getEntryIdentifier(): String {
        throw UnsupportedOperationException()
    }
}