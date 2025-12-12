package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.effects.AppliedPotionEffect
import gg.thronebound.dockyard.extentions.*
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.readOptional
import gg.thronebound.dockyard.protocol.writeOptional
import gg.thronebound.dockyard.registry.registries.PotionType
import gg.thronebound.dockyard.registry.registries.PotionTypeRegistry
import io.github.dockyardmc.scroll.CustomColor
import io.netty.buffer.ByteBuf

class PotionContentsComponent(
    val potion: PotionType?,
    val customColor: CustomColor?,
    val effects: List<AppliedPotionEffect>,
    val customName: String?
) : DataComponent() {

    override fun hashStruct(): HashHolder {
        return CRC32CHasher.of {
            optional("potion", potion, CRC32CHasher::ofRegistryEntry)
            optional("custom_color", customColor, CRC32CHasher::ofColor)
            defaultStructList("custom_effects", effects, listOf(), AppliedPotionEffect::hashStruct)
            optional("custom_name", customName, CRC32CHasher::ofString)
        }
    }

    override fun write(buffer: ByteBuf) {
        buffer.writeOptional(potion?.getProtocolId(), ByteBuf::writeVarInt)
        buffer.writeOptional(customColor, CustomColor::writePackedInt)
        AppliedPotionEffect.STREAM_CODEC.list().write(buffer, effects)
        buffer.writeOptional(customName, ByteBuf::writeString)
    }

    companion object : NetworkReadable<PotionContentsComponent> {
        override fun read(buffer: ByteBuf): PotionContentsComponent {
            return PotionContentsComponent(
                buffer.readOptional(ByteBuf::readVarInt)?.let { PotionTypeRegistry.getByProtocolId(it) },
                buffer.readOptional(ByteBuf::readInt)?.let { CustomColor.fromRGBInt(it) },
                AppliedPotionEffect.STREAM_CODEC.list().read(buffer),
                buffer.readOptional(ByteBuf::readString)
            )
        }
    }
}