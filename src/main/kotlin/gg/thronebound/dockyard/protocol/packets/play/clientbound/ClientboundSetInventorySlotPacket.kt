package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundSetInventorySlotPacket(slot: Int, itemStack: ItemStack) : ClientboundPacket() {

    init {
        require(slot >= 0) { "Slot cannot be negative" }

        buffer.writeVarInt(slot)
        itemStack.write(buffer)
    }
}