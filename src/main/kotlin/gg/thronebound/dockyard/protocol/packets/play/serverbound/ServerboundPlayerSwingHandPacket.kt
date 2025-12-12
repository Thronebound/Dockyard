package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PlayerSwingHandEvent
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundPlayerAnimationPacket
import gg.thronebound.dockyard.protocol.packets.play.clientbound.EntityAnimation
import gg.thronebound.dockyard.utils.getPlayerEventContext
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundPlayerSwingHandPacket(val hand: PlayerHand) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val player = processor.player
        Events.dispatch(PlayerSwingHandEvent(player, hand, getPlayerEventContext(player)))
        val animation = if (hand == PlayerHand.MAIN_HAND) EntityAnimation.SWING_MAIN_ARM else EntityAnimation.SWING_OFFHAND
        val packet = ClientboundPlayerAnimationPacket(player, animation)
        player.viewers.forEach { it.sendPacket(packet) }
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundPlayerSwingHandPacket {
            return ServerboundPlayerSwingHandPacket(buf.readEnum<PlayerHand>())
        }
    }
}