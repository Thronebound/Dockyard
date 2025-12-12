package gg.thronebound.dockyard.protocol.packets.configurations

import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.NetworkWritable
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.protocol.types.writeList
import gg.thronebound.dockyard.registry.Registry
import gg.thronebound.dockyard.registry.RegistryEntry
import gg.thronebound.dockyard.registry.RegistryManager
import gg.thronebound.dockyard.registry.registries.BlockRegistry
import gg.thronebound.dockyard.registry.registries.RegistryBlock
import gg.thronebound.dockyard.registry.registries.tags.TagRegistry
import io.netty.buffer.ByteBuf
import kotlinx.serialization.Serializable
import net.kyori.adventure.nbt.BinaryTag

class ClientboundUpdateTagsPacket(val registries: List<TagRegistry>) : ClientboundPacket() {

    init {
        buffer.writeVarInt(registries.size)
        registries.forEach { registry ->
            buffer.writeString(registry.identifier)
            buffer.writeList<Tag>(registry.getEntries().keyToValue().values.toList()) { buffer: ByteBuf, tag -> tag.write(buffer) }
        }
    }
}


@Serializable
data class Tag(
    val identifier: String,
    val tags: Set<String>,
    val registryIdentifier: String,
) : NetworkWritable, RegistryEntry {

    override fun getNbt(): BinaryTag? = null
    override fun getProtocolId(): Int = -1

    override fun getEntryIdentifier(): String {
        return identifier
    }

    operator fun contains(identifier: String): Boolean {
        return tags.contains(identifier)
    }

    override fun toString(): String {
        return "#$identifier"
    }

    override fun write(buffer: ByteBuf) {
        val registry = RegistryManager.getFromIdentifier<Registry<*>>(registryIdentifier)
        buffer.writeString(identifier)
        val intTags = tags.map { tag ->
            val entry = registry[tag]
            if (registry is BlockRegistry) (entry as RegistryBlock).getLegacyProtocolId() else entry.getProtocolId()
        }
        buffer.writeList(intTags, ByteBuf::writeVarInt)
    }
}
