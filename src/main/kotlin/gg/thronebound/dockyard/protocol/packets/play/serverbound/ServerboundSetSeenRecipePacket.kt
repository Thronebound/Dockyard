package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundSetSeenRecipePacket(identifier: String) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {

    }

    companion object {
        fun read(byteBuf: ByteBuf): ServerboundSetSeenRecipePacket = ServerboundSetSeenRecipePacket(byteBuf.readString())
    }
}