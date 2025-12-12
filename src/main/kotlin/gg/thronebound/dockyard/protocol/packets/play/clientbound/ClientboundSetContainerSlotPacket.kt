package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundSetContainerSlotPacket(slot: Int, itemStack: ItemStack): ClientboundPacket() {

    init {
        buffer.writeVarInt(1)
        buffer.writeVarInt(0)
        buffer.writeShort(slot)
        itemStack.write(buffer)
    }
}