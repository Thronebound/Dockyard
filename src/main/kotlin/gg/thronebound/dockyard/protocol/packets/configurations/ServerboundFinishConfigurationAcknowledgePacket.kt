package gg.thronebound.dockyard.protocol.packets.configurations

import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundFinishConfigurationAcknowledgePacket: ServerboundPacket {
    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        processor.configurationHandler.handleConfigurationFinishAcknowledge(this, connection)
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundFinishConfigurationAcknowledgePacket {
            buf.readBytes(0)
            return ServerboundFinishConfigurationAcknowledgePacket()
        }
    }
}