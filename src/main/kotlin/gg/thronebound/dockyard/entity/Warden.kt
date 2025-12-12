package gg.thronebound.dockyard.entity

import cz.lukynka.bindables.Bindable
import gg.thronebound.dockyard.entity.metadata.EntityMetaValue
import gg.thronebound.dockyard.entity.metadata.EntityMetadata
import gg.thronebound.dockyard.entity.metadata.EntityMetadataType
import gg.thronebound.dockyard.extentions.sendPacket
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.EntityPose
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundEntityEventPacket
import gg.thronebound.dockyard.protocol.packets.play.clientbound.EntityEvent
import gg.thronebound.dockyard.registry.EntityTypes
import gg.thronebound.dockyard.registry.registries.EntityType

open class Warden(location: Location) : Entity(location) {
    override var type: EntityType = EntityTypes.WARDEN
    override val health: Bindable<Float> = Bindable(500f)
    override var inventorySize: Int = 0

    val angerLevel: Bindable<Int> = Bindable(0)

    init {
        angerLevel.valueChanged {
            metadata[EntityMetadataType.WARDEN_ANGER_LEVEL] = EntityMetadata(EntityMetadataType.WARDEN_ANGER_LEVEL, EntityMetaValue.VAR_INT, it.newValue)
        }
    }

    fun playAnimation(animation: WardenAnimation) {

        when (animation) {
            WardenAnimation.EMERGE -> pose.value = EntityPose.EMERGING
            WardenAnimation.ROAR -> pose.value = EntityPose.ROARING
            WardenAnimation.SNIFF -> pose.value = EntityPose.SNIFFING
            WardenAnimation.DIGGING -> pose.value = EntityPose.DIGGING
            WardenAnimation.ATTACK -> viewers.sendPacket(ClientboundEntityEventPacket(this, EntityEvent.WARDEN_ATTACK))
            WardenAnimation.SONIC_BOOM -> viewers.sendPacket(ClientboundEntityEventPacket(this, EntityEvent.WARDEN_SONIC_BOOM))
            WardenAnimation.TENDRIL_SHAKE -> viewers.sendPacket(ClientboundEntityEventPacket(this, EntityEvent.WARDEN_TENDRIL_SHAKING))
        }
    }
}

enum class WardenAnimation {
    EMERGE,
    ROAR,
    SNIFF,
    DIGGING,
    ATTACK,
    SONIC_BOOM,
    TENDRIL_SHAKE
}