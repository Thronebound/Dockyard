package gg.thronebound.dockyard.entity

import cz.lukynka.bindables.Bindable
import gg.thronebound.dockyard.entity.metadata.EntityMetaValue
import gg.thronebound.dockyard.entity.metadata.EntityMetadata
import gg.thronebound.dockyard.entity.metadata.EntityMetadataType
import gg.thronebound.dockyard.extentions.sendPacket
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundWorldEventPacket
import gg.thronebound.dockyard.protocol.packets.play.clientbound.WorldEvent
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.registry.EntityTypes
import gg.thronebound.dockyard.registry.Items
import gg.thronebound.dockyard.registry.registries.EntityType

class Parrot(location: Location): Entity(location) {
    override var type: EntityType = EntityTypes.PARROT
    override val health: Bindable<Float> = Bindable(6f)
    override var inventorySize: Int = 0
    val variant: Bindable<ParrotVariant> = Bindable(ParrotVariant.entries.random())

    init {
        variant.valueChanged {
            metadata[EntityMetadataType.PARROT_VARIANT] = EntityMetadata(EntityMetadataType.PARROT_VARIANT, EntityMetaValue.VAR_INT, it.newValue.ordinal)
        }
        variant.triggerUpdate()
    }

    fun makeDance() {
        val jukeboxLoc = location.subtract(0, 2, 0)
        jukeboxLoc.world.setBlock(jukeboxLoc, Blocks.JUKEBOX)
        val recordPacket = ClientboundWorldEventPacket(WorldEvent.PLAY_RECORD, jukeboxLoc, Items.MUSIC_DISC_CREATOR.getProtocolId(), false)
        viewers.sendPacket(recordPacket)
    }
}

enum class ParrotVariant {
    RED_BLUE,
    BLUE,
    GREEN,
    YELLOW_BLUE,
    GRAY;
}