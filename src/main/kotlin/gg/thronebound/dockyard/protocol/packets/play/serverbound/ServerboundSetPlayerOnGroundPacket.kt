package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.utils.bitMask
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundSetPlayerOnGroundPacket(
    val isOnGround: Boolean,
    val horizontalCollision: Boolean,
) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        processor.player.isOnGround = isOnGround
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundSetPlayerOnGroundPacket {
            val mask = buf.readByte()

            val isOnGround = bitMask(mask, 1)
            val isHorizontalCollision = bitMask(mask, 2)

            return ServerboundSetPlayerOnGroundPacket(isOnGround, isHorizontalCollision)
        }
    }

}