package gg.thronebound.dockyard.registry.registries

import gg.thronebound.dockyard.dialog.Dialog
import gg.thronebound.dockyard.protocol.packets.configurations.ClientboundRegistryDataPacket
import gg.thronebound.dockyard.registry.DynamicRegistry
import gg.thronebound.dockyard.registry.RegistryEntry
import net.kyori.adventure.nbt.CompoundBinaryTag

object DialogRegistry : DynamicRegistry<DialogEntry>() {
    override val identifier: String = "minecraft:dialog"

    override fun updateCache() {
        cachedPacket = ClientboundRegistryDataPacket(this)
    }
}

data class DialogEntry(
    val identifier: String,
    val dialog: Dialog
) : RegistryEntry {
    override fun getNbt(): CompoundBinaryTag {
        return dialog.getNbt()
    }

    override fun getProtocolId(): Int {
        return DialogRegistry.getProtocolIdByEntry(this)
    }

    override fun getEntryIdentifier(): String {
        return identifier
    }
}
