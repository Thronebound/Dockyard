package gg.thronebound.dockyard.entity

import cz.lukynka.bindables.Bindable
import gg.thronebound.dockyard.entity.metadata.EntityMetaValue
import gg.thronebound.dockyard.entity.metadata.EntityMetadata
import gg.thronebound.dockyard.entity.metadata.EntityMetadataType
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundPlayerAnimationPacket
import gg.thronebound.dockyard.protocol.packets.play.clientbound.EntityAnimation
import gg.thronebound.dockyard.registry.EntityTypes
import gg.thronebound.dockyard.registry.registries.EntityType

open class Zombie(location: Location) : Entity(location) {
    override var type: EntityType = EntityTypes.ZOMBIE
    override val health: Bindable<Float> = bindablePool.provideBindable(20f)
    override var inventorySize: Int = 0

    val raisedArms: Bindable<Boolean> = bindablePool.provideBindable(false)

    fun swingHands() {
        this.sendPacketToViewers(ClientboundPlayerAnimationPacket(this, EntityAnimation.SWING_MAIN_ARM))
    }

    init {
        raisedArms.valueChanged { event ->
            if (event.newValue) {
                this.metadata[EntityMetadataType.ZOMBIE_RAISED_ARMS] = EntityMetadata(EntityMetadataType.ZOMBIE_RAISED_ARMS, EntityMetaValue.BYTE, 0x0)
            } else {
                this.metadata.remove(EntityMetadataType.ZOMBIE_RAISED_ARMS)
            }
        }
    }
}