package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.entity.ItemDropEntity
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundPickupItemPacket(val collected: ItemDropEntity, val collector: Entity, val item: ItemStack): ClientboundPacket() {

    init {
        buffer.writeVarInt(collected.id)
        buffer.writeVarInt(collector.id)
        buffer.writeVarInt(item.amount)
    }

}