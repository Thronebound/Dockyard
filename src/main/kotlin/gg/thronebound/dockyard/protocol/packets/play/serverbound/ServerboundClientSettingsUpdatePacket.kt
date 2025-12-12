package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PlayerClientSettingsEvent
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.protocol.types.ClientSettings
import io.github.dockyardmc.tide.stream.StreamCodec
import gg.thronebound.dockyard.utils.getPlayerEventContext
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

data class ServerboundClientSettingsUpdatePacket(val clientSettings: ClientSettings) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val event = PlayerClientSettingsEvent(clientSettings, processor.player, getPlayerEventContext(processor.player))
        Events.dispatch(event)

        processor.player.clientSettings = clientSettings
    }

    companion object : NetworkReadable<ServerboundClientSettingsUpdatePacket> {

        val STREAM_CODEC = StreamCodec.of(
            ClientSettings.STREAM_CODEC, ServerboundClientSettingsUpdatePacket::clientSettings,
            ::ServerboundClientSettingsUpdatePacket
        )

        override fun read(buffer: ByteBuf): ServerboundClientSettingsUpdatePacket {
            return STREAM_CODEC.read(buffer)
        }
    }
}