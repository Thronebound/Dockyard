package gg.thronebound.dockyard.protocol.packets.configurations

import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.protocol.plugin.PluginMessageRegistry
import gg.thronebound.dockyard.protocol.plugin.messages.PluginMessage
import io.github.dockyardmc.tide.stream.StreamCodec
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundConfigurationPluginMessagePacket(val contents: PluginMessage.Contents) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        PluginMessageRegistry.handle<PluginMessage>(contents, processor)
    }

    companion object {
        val STREAM_CODEC = StreamCodec.of(
            PluginMessage.Contents.STREAM_CODEC, ServerboundConfigurationPluginMessagePacket::contents,
            ::ServerboundConfigurationPluginMessagePacket
        )

        fun read(buffer: ByteBuf): ServerboundConfigurationPluginMessagePacket {
            return STREAM_CODEC.read(buffer)
        }
    }
}