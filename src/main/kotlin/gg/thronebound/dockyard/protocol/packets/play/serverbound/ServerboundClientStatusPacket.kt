package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundClientStatusPacket(val action: ClientStatusAction) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        if (action == ClientStatusAction.RESPAWN && processor.player.isDead) {
            processor.player.respawn(true)
        }
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundClientStatusPacket = ServerboundClientStatusPacket(buf.readEnum<ClientStatusAction>())
    }
}

enum class ClientStatusAction {
    RESPAWN,
    REQUEST_STATS
}