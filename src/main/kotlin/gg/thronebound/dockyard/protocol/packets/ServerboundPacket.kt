package gg.thronebound.dockyard.protocol.packets

import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import io.netty.channel.ChannelHandlerContext

interface ServerboundPacket {
     fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int)
}