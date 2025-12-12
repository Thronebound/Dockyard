package gg.thronebound.dockyard.entity.handlers

import cz.lukynka.bindables.BindableList
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.events.EntityDismountVehicleEvent
import gg.thronebound.dockyard.events.EntityRideVehicleEvent
import gg.thronebound.dockyard.events.Event
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundSetPassengersPacket

class EntityVehicleHandler(override val entity: Entity) : EntityHandler {

    fun handle(passengers: BindableList<Entity>) {

        passengers.itemAdded {

            val contextPlayers = mutableSetOf<Player>()
            if (entity is Player) contextPlayers.add(entity)
            if (it.item is Player) contextPlayers.add(it.item as Player)

            val event = EntityRideVehicleEvent(
                entity, it.item, Event.Context(
                    contextPlayers,
                    setOf(entity, it.item),
                    setOf(entity.world),
                    setOf(entity.location),
                )
            )

            Events.dispatch(event)
            if (event.cancelled) {
                passengers.remove(it.item)
                return@itemAdded
            }

            it.item.vehicle = entity
            val packet = ClientboundSetPassengersPacket(entity, passengers.values)
            entity.sendPacketToViewers(packet)
        }


        passengers.itemRemoved {
            val contextPlayers = mutableSetOf<Player>()
            if(entity is Player) contextPlayers.add(entity)
            if(it.item is Player) contextPlayers.add(it.item as Player)

            val event = EntityDismountVehicleEvent(entity, it.item, Event.Context(
                contextPlayers,
                setOf(entity, it.item),
                setOf(entity.world),
                setOf(entity.location),
            ))

            Events.dispatch(event)
            if(event.cancelled) {
                passengers.remove(it.item)
                return@itemRemoved
            }

            it.item.vehicle = null
            val packet = ClientboundSetPassengersPacket(entity, passengers.values)
            entity.sendPacketToViewers(packet)
        }
    }
}