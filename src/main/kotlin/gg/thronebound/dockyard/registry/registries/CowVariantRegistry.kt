package gg.thronebound.dockyard.registry.registries

import gg.thronebound.dockyard.nbt.nbt
import gg.thronebound.dockyard.registry.DataDrivenRegistry
import gg.thronebound.dockyard.registry.RegistryEntry
import kotlinx.serialization.Serializable
import net.kyori.adventure.nbt.CompoundBinaryTag

object CowVariantRegistry : DataDrivenRegistry<CowVariant>() {
    override val identifier: String = "minecraft:cow_variant"
}

@Serializable
data class CowVariant(
    val identifier: String,
    val assetId: String,
) : RegistryEntry {

    override fun getProtocolId(): Int {
        return CowVariantRegistry.getProtocolIdByEntry(this)
    }

    override fun getEntryIdentifier(): String {
        return identifier
    }


    override fun getNbt(): CompoundBinaryTag {
        return nbt {
            withString("asset_id", assetId)
        }
    }
}