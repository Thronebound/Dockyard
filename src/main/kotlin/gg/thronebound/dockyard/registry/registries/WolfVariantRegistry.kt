package gg.thronebound.dockyard.registry.registries

import gg.thronebound.dockyard.nbt.nbt
import gg.thronebound.dockyard.registry.DataDrivenRegistry
import gg.thronebound.dockyard.registry.RegistryEntry
import kotlinx.serialization.Serializable
import net.kyori.adventure.nbt.CompoundBinaryTag

object WolfVariantRegistry : DataDrivenRegistry<WolfVariant>() {

    override val identifier: String = "minecraft:wolf_variant"

}

@Serializable
data class WolfVariant(
    val identifier: String,
    val angry: String,
    val tame: String,
    val wild: String,
) : RegistryEntry {

    override fun getProtocolId(): Int {
        return WolfVariantRegistry.getProtocolIdByEntry(this)
    }

    override fun getEntryIdentifier(): String {
        return identifier
    }


    override fun getNbt(): CompoundBinaryTag {
        return nbt {
            withCompound("assets") {
                withString("angry", angry)
                withString("tame", tame)
                withString("wild", wild)
            }
        }
    }
}