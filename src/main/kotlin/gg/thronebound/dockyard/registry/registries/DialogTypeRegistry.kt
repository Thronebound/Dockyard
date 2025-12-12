package gg.thronebound.dockyard.registry.registries

import gg.thronebound.dockyard.protocol.packets.configurations.ClientboundRegistryDataPacket
import gg.thronebound.dockyard.registry.DynamicRegistry
import gg.thronebound.dockyard.registry.RegistryEntry
import net.kyori.adventure.nbt.CompoundBinaryTag
import java.util.concurrent.atomic.AtomicInteger

object DialogTypeRegistry : DynamicRegistry<DialogType>() {
    override val identifier: String = "minecraft:dialog_type"

    private val dialogTypes: MutableMap<String, DialogType> = mutableMapOf()
    private val _protocolIds: MutableMap<String, Int> = mutableMapOf()
    private val protocolIdCounter = AtomicInteger()

    val protocolIds get() = _protocolIds.toMap()

    init {
        addEntry(DialogType("minecraft:notice"))
        addEntry(DialogType("minecraft:server_links"))
        addEntry(DialogType("minecraft:dialog_list"))
        addEntry(DialogType("minecraft:multi_action"))
        addEntry(DialogType("minecraft:confirmation"))
    }

    override fun updateCache() {
        cachedPacket = ClientboundRegistryDataPacket(this)
    }
}

data class DialogType(
    val identifier: String,
) : RegistryEntry {

    override fun getNbt(): CompoundBinaryTag? {
        return null
    }

    override fun getProtocolId(): Int {
        return DialogTypeRegistry.getProtocolIdByEntry(this)
    }

    override fun getEntryIdentifier(): String {
        return identifier
    }
}
