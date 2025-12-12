package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.extentions.*
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.readOptional
import gg.thronebound.dockyard.protocol.types.EquipmentSlot
import gg.thronebound.dockyard.protocol.writeOptional
import gg.thronebound.dockyard.protocol.writeOptionalList
import gg.thronebound.dockyard.registry.Sounds
import gg.thronebound.dockyard.registry.registries.EntityType
import gg.thronebound.dockyard.registry.registries.EntityTypeRegistry
import gg.thronebound.dockyard.sounds.BuiltinSoundEvent
import gg.thronebound.dockyard.sounds.SoundEvent
import io.netty.buffer.ByteBuf

class EquippableComponent(
    val equipmentSlot: EquipmentSlot,
    val equipSound: SoundEvent,
    val assetId: String?,
    val cameraOverlay: String?,
    val allowedEntities: List<EntityType>?,
    val dispensable: Boolean,
    val swappable: Boolean,
    val damageOnHurt: Boolean,
    val equipOnInteract: Boolean,
    val canBeSheared: Boolean,
    val shearingSound: SoundEvent
) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeEnum(equipmentSlot)
        SoundEvent.STREAM_CODEC.write(buffer, equipSound)
        buffer.writeOptional(assetId, ByteBuf::writeString)
        buffer.writeOptional(cameraOverlay, ByteBuf::writeString)
        buffer.writeOptionalList(allowedEntities?.map { type -> type.getProtocolId() }, ByteBuf::writeVarInt)
        buffer.writeBoolean(dispensable)
        buffer.writeBoolean(swappable)
        buffer.writeBoolean(damageOnHurt)
        buffer.writeBoolean(equipOnInteract)
        buffer.writeBoolean(canBeSheared)
        SoundEvent.STREAM_CODEC.write(buffer, shearingSound)
    }

    override fun hashStruct(): HashHolder {
        return CRC32CHasher.of {
            static("slot", CRC32CHasher.ofEnum(equipmentSlot))
            defaultStruct<SoundEvent>("equip_sound", BuiltinSoundEvent(Sounds.ITEM_ARMOR_EQUIP_GENERIC), equipSound, SoundEvent::hashStruct)
            optional("asset_id", assetId, CRC32CHasher::ofString)
            optional("camera_overlay", cameraOverlay, CRC32CHasher::ofString)
            optionalList("allowed_entities", allowedEntities, CRC32CHasher::ofRegistryEntry)
            default("dispensable", true, dispensable, CRC32CHasher::ofBoolean)
            default("swappable", true, swappable, CRC32CHasher::ofBoolean)
            default("damage_on_hurt", true, damageOnHurt, CRC32CHasher::ofBoolean)
            default("equip_on_interact", false, equipOnInteract, CRC32CHasher::ofBoolean)
            default("can_be_sheared", false, canBeSheared, CRC32CHasher::ofBoolean)
            defaultStruct("shearing_sound", DEFAULT_SHEARING_SOUND, shearingSound, SoundEvent::hashStruct)
        }
    }

    companion object : NetworkReadable<EquippableComponent> {

        val DEFAULT_SHEARING_SOUND = BuiltinSoundEvent(Sounds.ITEM_SHEARS_SNIP)

        override fun read(buffer: ByteBuf): EquippableComponent {
            return EquippableComponent(
                buffer.readEnum(),
                SoundEvent.STREAM_CODEC.read(buffer),
                buffer.readOptional(ByteBuf::readString),
                buffer.readOptional(ByteBuf::readString),
                buffer.readOptional { b -> b.readList(ByteBuf::readVarInt).map { int -> EntityTypeRegistry.getByProtocolId(int) } },
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                SoundEvent.STREAM_CODEC.read(buffer)
            )
        }
    }
}