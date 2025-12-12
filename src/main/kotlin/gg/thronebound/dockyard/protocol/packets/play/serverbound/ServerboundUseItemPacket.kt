package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PlayerRightClickWithItemEvent
import gg.thronebound.dockyard.extentions.broadcastMessage
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.player.systems.startConsumingIfApplicable
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.utils.getPlayerEventContext
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundUseItemPacket(val hand: PlayerHand, val sequence: Int, val yaw: Float, val pitch: Float): ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val player = processor.player
        val item = player.getHeldItem(PlayerHand.MAIN_HAND)

        val event = PlayerRightClickWithItemEvent(player, item, getPlayerEventContext(player))
        Events.dispatch(event)
        if(event.cancelled) return

        startConsumingIfApplicable(item, player)
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundUseItemPacket =
            ServerboundUseItemPacket(buf.readEnum<PlayerHand>(), buf.readVarInt(), buf.readFloat(), buf.readFloat())
    }
}