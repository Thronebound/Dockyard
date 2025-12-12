package gg.thronebound.dockyard.registry.registries

import gg.thronebound.dockyard.nbt.nbt
import gg.thronebound.dockyard.registry.DataDrivenRegistry
import gg.thronebound.dockyard.registry.RegistryEntry
import kotlinx.serialization.Serializable
import net.kyori.adventure.nbt.CompoundBinaryTag

object DamageTypeRegistry : DataDrivenRegistry<DamageType>() {
    override val identifier: String = "minecraft:damage_type"
}

@Serializable
data class DamageType(
    val identifier: String,
    val exhaustion: Float,
    val messageId: String,
    val scaling: String,
    val effects: String? = null,
    val deathMessageType: String? = null,
) : RegistryEntry {

    override fun getProtocolId(): Int {
        return DamageTypeRegistry.getProtocolIdByEntry(this)
    }

    override fun getEntryIdentifier(): String {
        return identifier
    }


    override fun getNbt(): CompoundBinaryTag {
        return nbt {
            withFloat("exhaustion", exhaustion)
            withString("message_id", messageId)
            withString("scaling", scaling)
        }
    }
}