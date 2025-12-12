package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundSetInventoryCursorPacket(item: ItemStack): ClientboundPacket() {

    init {
        item.write(buffer)
    }

}