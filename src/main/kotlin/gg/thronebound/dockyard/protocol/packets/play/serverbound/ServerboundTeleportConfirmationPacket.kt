package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundTeleportConfirmationPacket(teleportId: Int): ServerboundPacket {
    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        processor.playHandler.handleTeleportConfirmation(this, connection)
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundTeleportConfirmationPacket =
            ServerboundTeleportConfirmationPacket(buf.readVarInt())
    }
}