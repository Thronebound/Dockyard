package gg.thronebound.dockyard.entity.handlers

import gg.thronebound.dockyard.config.ConfigManager
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.entity.EntityManager.despawnEntity
import gg.thronebound.dockyard.entity.ItemDropEntity
import gg.thronebound.dockyard.events.EntityPickupItemEvent
import gg.thronebound.dockyard.events.Event
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.extentions.sendPacket
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundPickupItemPacket

class EntityItemPickupHandler(override val entity: Entity) : TickableEntityHandler {

    override fun tick() {
        synchronized(entity) {
            val world = entity.world
            val location = entity.location

            if(!ConfigManager.config.implementationConfig.itemDroppingAndPickup) return
            val drops = world.entities.filterIsInstance<ItemDropEntity>()
            if (entity.inventorySize <= 0) return
            drops.toList().forEach { drop ->
                if (entity is Player && !drop.viewers.contains(entity)) return@forEach
                if (!drop.canBePickedUp) return@forEach
                if (drop.location.distance(location) > drop.pickupDistance) return@forEach

                val itemStack = drop.itemStack.value

                val eventContext = Event.Context(
                    setOf(),
                    setOf(drop, entity),
                    setOf(entity.world),
                    setOf(entity.location, drop.location)
                )
                val event = EntityPickupItemEvent(entity, drop, eventContext)
                Events.dispatch(event)
                if (event.cancelled) return@forEach

                if (entity.canPickupItem(drop, itemStack)) {
                    val mutualViewers = drop.viewers.filter { entity.viewers.contains(it) }
                    if (drop.pickupAnimation) {
                        val packet = ClientboundPickupItemPacket(drop, entity, itemStack)
                        mutualViewers.sendPacket(packet)
                        if (entity is Player) entity.sendPacket(packet)
                    }
                    drop.world.despawnEntity(drop)
                    return@forEach
                }
            }
        }
    }
}
