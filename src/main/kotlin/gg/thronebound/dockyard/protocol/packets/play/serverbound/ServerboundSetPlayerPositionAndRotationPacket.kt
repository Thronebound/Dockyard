package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundSetPlayerPositionAndRotationPacket(
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float,
    val pitch: Float,
    val isOnGround: Boolean
) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        processor.playHandler.handlePlayerPositionAndRotationUpdates(this, connection)
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundSetPlayerPositionAndRotationPacket {
            return ServerboundSetPlayerPositionAndRotationPacket(
                buf.readDouble(),
                buf.readDouble(),
                buf.readDouble(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readBoolean()
            )
        }
    }
}