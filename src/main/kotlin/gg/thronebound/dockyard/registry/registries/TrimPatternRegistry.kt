package gg.thronebound.dockyard.registry.registries

import gg.thronebound.dockyard.nbt.nbt
import gg.thronebound.dockyard.registry.DataDrivenRegistry
import gg.thronebound.dockyard.registry.RegistryEntry
import io.github.dockyardmc.scroll.Component
import gg.thronebound.dockyard.utils.kotlinx.ComponentToJsonSerializer
import kotlinx.serialization.Serializable
import net.kyori.adventure.nbt.CompoundBinaryTag

object TrimPatternRegistry : DataDrivenRegistry<TrimPattern>() {
    override val identifier: String = "minecraft:trim_pattern"
}

@Serializable
data class TrimPattern(
    val identifier: String,
    val assetId: String,
    val decal: Boolean,
    @Serializable(with = ComponentToJsonSerializer::class)
    val description: Component,
) : RegistryEntry {

    override fun getProtocolId(): Int {
        return TrimPatternRegistry.getProtocolIdByEntry(this)
    }

    override fun getEntryIdentifier(): String {
        return identifier
    }

    override fun getNbt(): CompoundBinaryTag {
        return nbt {
            withString("asset_id", assetId)
            withBoolean("decal", decal)
            withCompound("description", description.toNBT())
        }
    }
}