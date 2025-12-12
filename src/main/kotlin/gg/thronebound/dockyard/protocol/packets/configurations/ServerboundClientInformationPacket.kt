package gg.thronebound.dockyard.protocol.packets.configurations

import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.protocol.types.ClientSettings
import io.github.dockyardmc.tide.stream.StreamCodec
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

data class ServerboundClientInformationPacket(
    val clientSettings: ClientSettings
) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        processor.configurationHandler.handleClientInformation(this, connection)
    }

    companion object {

        val STREAM_CODEC = StreamCodec.of(
            ClientSettings.STREAM_CODEC, ServerboundClientInformationPacket::clientSettings,
            ::ServerboundClientInformationPacket
        )

        fun read(buffer: ByteBuf): ServerboundClientInformationPacket {
            return STREAM_CODEC.read(buffer)
        }
    }
}