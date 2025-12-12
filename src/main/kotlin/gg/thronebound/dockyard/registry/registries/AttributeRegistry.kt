package gg.thronebound.dockyard.registry.registries

import gg.thronebound.dockyard.registry.DataDrivenRegistry
import gg.thronebound.dockyard.registry.RegistryEntry
import kotlinx.serialization.Serializable
import net.kyori.adventure.nbt.BinaryTag

object AttributeRegistry : DataDrivenRegistry<Attribute>() {
    override val identifier: String = "minecraft:attribute"
}

@Serializable
data class Attribute(
    val identifier: String,
    val translationKey: String,
    val defaultValue: Double,
    val clientSync: Boolean,
    val minValue: Double? = null,
    val maxValue: Double? = null
) : RegistryEntry {

    override fun getNbt(): BinaryTag? {
        return null
    }

    override fun getProtocolId(): Int {
        return AttributeRegistry.getProtocolIdByEntry(this)
    }

    override fun getEntryIdentifier(): String {
        return identifier
    }
}