package gg.thronebound.dockyard.noxesium.protocol.serverbound

import com.noxcrew.noxesium.api.protocol.ClientSettings
import gg.thronebound.dockyard.noxesium.protocol.NoxesiumCodecs
import gg.thronebound.dockyard.protocol.plugin.messages.PluginMessage
import io.github.dockyardmc.tide.stream.StreamCodec

data class ServerboundNoxesiumClientSettingsPacket(val clientSettings: ClientSettings) : PluginMessage {

    override fun getStreamCodec(): StreamCodec<out PluginMessage> {
        return STREAM_CODEC
    }

    companion object {
        val STREAM_CODEC = StreamCodec.of(
            NoxesiumCodecs.CLIENT_SETTINGS, ServerboundNoxesiumClientSettingsPacket::clientSettings,
            ::ServerboundNoxesiumClientSettingsPacket
        )
    }
}