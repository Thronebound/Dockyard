package gg.thronebound.dockyard.registry.registries

import gg.thronebound.dockyard.dialog.body.DialogBody
import gg.thronebound.dockyard.dialog.body.DialogItemBody
import gg.thronebound.dockyard.dialog.body.PlainMessage
import gg.thronebound.dockyard.protocol.packets.configurations.ClientboundRegistryDataPacket
import gg.thronebound.dockyard.registry.DynamicRegistry
import gg.thronebound.dockyard.registry.RegistryEntry
import net.kyori.adventure.nbt.CompoundBinaryTag
import kotlin.reflect.KClass

object DialogBodyTypeRegistry : DynamicRegistry<DialogBodyType>() {
    override val identifier: String = "minecraft:dialog_body_type"

    init {
        addEntry(DialogBodyType("minecraft:item", DialogItemBody::class))
        addEntry(DialogBodyType("minecraft:plain_message", PlainMessage::class))
    }

    override fun updateCache() {
        cachedPacket = ClientboundRegistryDataPacket(this)
    }
}

data class DialogBodyType(
    val identifier: String,
    val clazz: KClass<out DialogBody>
) : RegistryEntry {
    override fun getNbt(): CompoundBinaryTag? {
        return null
    }

    override fun getProtocolId(): Int {
        return DialogBodyTypeRegistry.getProtocolIdByEntry(this)
    }

    override fun getEntryIdentifier(): String {
        return identifier
    }
}