package gg.thronebound.dockyard.registry.registries

import gg.thronebound.dockyard.nbt.nbt
import gg.thronebound.dockyard.registry.DataDrivenRegistry
import gg.thronebound.dockyard.registry.RegistryEntry
import kotlinx.serialization.Serializable
import net.kyori.adventure.nbt.CompoundBinaryTag

object CatVariantRegistry : DataDrivenRegistry<CatVariant>() {
    override val identifier: String = "minecraft:cat_variant"
}

@Serializable
data class CatVariant(
    val identifier: String,
    val assetId: String,
) : RegistryEntry {

    override fun getEntryIdentifier(): String {
        return identifier
    }

    override fun getProtocolId(): Int {
        return CatVariantRegistry.getProtocolIdByEntry(this)
    }

    override fun getNbt(): CompoundBinaryTag {
        return nbt {
            withString("asset_id", assetId)
        }
    }
}