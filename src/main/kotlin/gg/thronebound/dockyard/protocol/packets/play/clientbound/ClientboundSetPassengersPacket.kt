package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundSetPassengersPacket(val vehicle: Entity, val passengers: Collection<Entity>): ClientboundPacket() {

    constructor(vehicle: Entity, vararg passengers: Entity): this(vehicle, passengers.toList())
    constructor(vehicle: Entity, passenger: Entity): this(vehicle, listOf(passenger))

    init {
        buffer.writeVarInt(vehicle.id)
        buffer.writeVarInt(passengers.size)
        passengers.forEach {
            buffer.writeVarInt(it.id)
        }
    }
}