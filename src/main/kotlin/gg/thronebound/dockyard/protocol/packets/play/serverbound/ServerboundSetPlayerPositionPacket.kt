package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.utils.bitMask
import gg.thronebound.dockyard.math.vectors.Vector3d
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundSetPlayerPositionPacket(
    val vector3d: Vector3d,
    val isOnGround: Boolean,
    val horizontalCollision: Boolean,
) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        processor.playHandler.handlePlayerPositionAndRotationUpdates(this, connection)
    }

    companion object {
        fun read(buffer: ByteBuf): ServerboundSetPlayerPositionPacket {
            val vector3d = Vector3d.read(buffer)
            val mask = buffer.readByte()

            val isOnGround = bitMask(mask, 1)
            val isHorizontalCollision = bitMask(mask, 2)

            return ServerboundSetPlayerPositionPacket(vector3d, isOnGround, isHorizontalCollision)
        }
    }
}