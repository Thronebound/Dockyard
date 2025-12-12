package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.protocol.types.writeList

class ClientboundSetContainerContentPacket(player: Player, items: List<ItemStack>) : ClientboundPacket() {

    init {
        buffer.writeVarInt(if(player.currentlyOpenScreen != null) 1 else 0)
        buffer.writeVarInt(0)
        buffer.writeList(items, ItemStack::write)
        player.inventory.cursorItem.value.write(buffer)
    }
}