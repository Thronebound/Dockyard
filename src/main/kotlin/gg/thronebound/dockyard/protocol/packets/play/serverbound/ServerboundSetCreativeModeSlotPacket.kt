package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.inventory.PlayerInventoryUtils
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.player.systems.GameMode
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundSetCreativeModeSlotPacket(var slot: Int, var clickedItem: ItemStack) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {

        val player = processor.player
        if(player.gameMode.value != GameMode.CREATIVE) return
        if(slot == -1) {
            //drop
            val cancelled = player.inventory.drop(clickedItem)
            if(cancelled) {
                player.inventory.sendFullInventoryUpdate()
                player.inventory.cursorItem.value = clickedItem
                return
            }
            return
        }

        if(slot < 1 || slot > PlayerInventoryUtils.OFFHAND_SLOT) {
            return
        }

        val newSlot = PlayerInventoryUtils.convertPlayerInventorySlot(slot, PlayerInventoryUtils.OFFSET)
        if(player.inventory[newSlot] == clickedItem) return

        val equipmentSlot = player.inventory.getEquipmentSlot(newSlot, player.heldSlotIndex.value)
        if(equipmentSlot != null) {
            player.equipment[equipmentSlot] = clickedItem
            player.inventory.cursorItem.value = ItemStack.AIR
            return
        }

        player.inventory[newSlot] = clickedItem
    }

    companion object {
        fun read(buffer: ByteBuf): ServerboundSetCreativeModeSlotPacket {
            val slot = buffer.readShort().toInt()
            val clickedItem = ItemStack.read(buffer, true, false)
            return ServerboundSetCreativeModeSlotPacket(slot, clickedItem)
        }
    }
}